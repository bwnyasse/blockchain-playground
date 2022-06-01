package net.bwnyasse;

import com.google.common.io.ByteStreams;
import com.hedera.hashgraph.sdk.*;

import io.github.cdimascio.dotenv.Dotenv;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

public class DeployContractWithHTS {

    public static final String SOLIDITY_BIN_OUT = "solidity/output/HTS.bin";

    public static void main(String[] args) throws TimeoutException, PrecheckStatusException,
            ReceiptStatusException, InterruptedException, IOException {

        // Create your Hedera testnet client
        Client client = BlockchainPlaygroundHederaCommon.getHederaClientForTestnet();

        // Create a treasury Key
        PrivateKey treasuryKey = PrivateKey.generateED25519();

        // Create a treasury account
        AccountCreateTransaction treasuryAccount = new AccountCreateTransaction()
                .setKey(treasuryKey)
                .setInitialBalance(new Hbar(100))
                .setAccountMemo("treasury account");

        // Submit the account create transaction
        TransactionResponse submitAccountCreateTx = treasuryAccount.execute(client);

        // Get the receipt of the transaction
        TransactionReceipt newAccountReceipt = submitAccountCreateTx.getReceipt(client);

        // Get the treasury account ID
        AccountId treasuryAccountId = newAccountReceipt.accountId;
        System.out.println("The new account ID is " + treasuryAccountId);

        // Create a token to interact with
        TokenCreateTransaction createToken = new TokenCreateTransaction()
                .setTokenName("HSCS demo")
                .setTokenSymbol("H")
                .setTokenType(TokenType.FUNGIBLE_COMMON)
                .setTreasuryAccountId(treasuryAccountId)
                .setInitialSupply(500);

        // Submit the token create transaction
        TransactionResponse submitTokenTx =
                createToken.freezeWith(client).sign(treasuryKey).execute(client);

        // Get the token ID
        TokenId tokenId = submitTokenTx.getReceipt(client).tokenId;
        System.out.println("The new token ID is " + tokenId);

        //
        // -- Store the Smart Contract Bytecode on Hedera
        //

        // Retrieve the smart contract byte code
        InputStream initialStream = DeployFirstSmartContratSample.class.getClassLoader()
                .getResourceAsStream(SOLIDITY_BIN_OUT);
        byte[] bytecode = ByteStreams.toByteArray(initialStream);

        // Create a file on Hedera and store the hex-encoded bytecode
        FileCreateTransaction fileCreateTx = new FileCreateTransaction()
                // Set the bytecode of the contract
                .setContents(bytecode);

        // Submit the file to the Hedera test network signing with the transaction fee payer key
        // specified with the client
        TransactionResponse submitTx = fileCreateTx.execute(client);

        // Get the receipt of the file create transaction
        TransactionReceipt fileReceipt = submitTx.getReceipt(client);

        // Get the file ID from the receipt
        FileId bytecodeFileId = fileReceipt.fileId;

        // Log the file ID
        System.out.println("The smart contract bytecode file ID is " + bytecodeFileId);

        //
        // -- Deploy a Hedera Smart Contract
        //

        // Deploy the contract
        ContractCreateTransaction contractTx = new ContractCreateTransaction()
                // The contract bytecode file
                .setBytecodeFileId(bytecodeFileId)
                // The max gas to reserve for this transaction
                .setGas(2_000_000);

        // Submit the transaction to the Hedera test network
        TransactionResponse contractResponse = contractTx.execute(client);

        // Get the receipt of the file create transaction
        TransactionReceipt contractReceipt = contractResponse.getReceipt(client);

        // Get the smart contract ID
        ContractId newContractId = contractReceipt.contractId;

        // Log the smart contract ID
        System.out.println("The smart contract ID is " + newContractId);


        //
        // -- Call the tokenAssociate Contract Function
        //
        // Associate the token to an account using the HTS contract
        ContractExecuteTransaction associateToken = new ContractExecuteTransaction()
                // The contract to call
                .setContractId(newContractId)
                // The gas for the transaction
                .setGas(2_000_000)
                // The contract function to call and parameters to pass
                .setFunction("tokenAssociate", new ContractFunctionParameters()
                        // The account ID to associate the token to
                        .addAddress(
                                BlockchainPlaygroundHederaCommon.getAccountId().toSolidityAddress())
                        // The token ID to associate to the account
                        .addAddress(tokenId.toSolidityAddress()));

        // Sign with the account key to associate and submit to the Hedera network
        TransactionResponse associateTokenResponse =
                associateToken.freezeWith(client)
                        .sign(BlockchainPlaygroundHederaCommon.getMyPrivateKey()).execute(client);

        System.out.println(
                "The transaction status: " + associateTokenResponse.getReceipt(client).status);

        //
        // - Get the tokenAssociate Transaction Record
        //

        // Get the child token associate transaction record
        TransactionRecord childRecords = new TransactionRecordQuery()
                // Set the bool flag equal to true
                .setIncludeChildren(true)
                // The transaction ID of th parent contract execute transaction
                .setTransactionId(associateTokenResponse.transactionId)
                .execute(client);

        System.out.println("The transaction record for the associate transaction"
                + childRecords.children.get(0));

        // The balance of the account
        AccountBalance accountBalance3 = new AccountBalanceQuery()
                .setAccountId(BlockchainPlaygroundHederaCommon.getAccountId())
                .execute(client);

        System.out.println("The " + tokenId + " should now be associated to my account: "
                + accountBalance3.tokens);


        //
        // - Call the tokenTransfer Contract Function
        //

        // Transfer the new token to the account
        // Contract function params need to be in the order of the paramters provided in the
        // tokenTransfer contract function
        ContractExecuteTransaction tokenTransfer = new ContractExecuteTransaction()
                .setContractId(newContractId)
                .setGas(2_000_000)
                .setFunction("tokenTransfer", new ContractFunctionParameters()
                        // The ID of the token
                        .addAddress(tokenId.toSolidityAddress())
                        // The account to transfer the tokens from
                        .addAddress(treasuryAccountId.toSolidityAddress())
                        // The account to transfer the tokens to
                        .addAddress(
                                BlockchainPlaygroundHederaCommon.getAccountId().toSolidityAddress())
                        // The number of tokens to transfer
                        .addInt64(100));

        // Sign the token transfer transaction with the treasury account to authorize the transfer
        // and submit
        ContractExecuteTransaction signTokenTransfer =
                tokenTransfer.freezeWith(client).sign(treasuryKey);

        // Submit transfer transaction
        TransactionResponse submitTransfer = signTokenTransfer.execute(client);

        // Get transaction status
        Status txStatus = submitTransfer.getReceipt(client).status;

        // Verify your account received the 100 tokens
        AccountBalance newAccountBalance = new AccountBalanceQuery()
                .setAccountId(BlockchainPlaygroundHederaCommon.getAccountId())
                .execute(client);

        System.out.println("My new account balance is " + newAccountBalance.tokens);

        // indicates successful termination
        Runtime.getRuntime().exit(0);
    }
}
