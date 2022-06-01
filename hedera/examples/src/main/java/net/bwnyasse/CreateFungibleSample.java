package net.bwnyasse;

import java.util.Collections;
import java.util.concurrent.TimeoutException;
import com.hedera.hashgraph.sdk.*;

public class CreateFungibleSample {

    public static void main(String[] args)
            throws TimeoutException, PrecheckStatusException, ReceiptStatusException {

        // Create your Hedera testnet client
        Client client = BlockchainPlaygroundHederaCommon.getHederaClientForTestnet();

        // Treasury Key
        PrivateKey treasuryKey = PrivateKey.generateED25519();
        PublicKey treasuryPublicKey = treasuryKey.getPublicKey();

        // Create treasury account
        TransactionResponse treasuryAccount = new AccountCreateTransaction()
                .setKey(treasuryPublicKey)
                .setInitialBalance(new Hbar(10))
                .execute(client);

        AccountId treasuryId = treasuryAccount.getReceipt(client).accountId;

        // Alice Key
        PrivateKey aliceKey = PrivateKey.generateED25519();
        PublicKey alicePublicKey = aliceKey.getPublicKey();

        // Create Alice's account
        TransactionResponse aliceAccount = new AccountCreateTransaction()
                .setKey(alicePublicKey)
                .setInitialBalance(new Hbar(10))
                .execute(client);

        AccountId aliceAccountId = aliceAccount.getReceipt(client).accountId;

        // Supply Key
        PrivateKey supplyKey = PrivateKey.generateED25519();
        // PublicKey supplyPublicKey = supplyKey.getPublicKey();

        // CREATE FUNGIBLE TOKEN (STABLECOIN)
        TokenCreateTransaction tokenCreateTx = new TokenCreateTransaction()
                .setTokenName("USD Bar")
                .setTokenSymbol("USDB")
                .setTokenType(TokenType.FUNGIBLE_COMMON)
                .setDecimals(2)
                .setInitialSupply(10000)
                .setTreasuryAccountId(treasuryId)
                .setSupplyType(TokenSupplyType.INFINITE)
                .setSupplyKey(supplyKey)
                .freezeWith(client);

        // Sign with the treasury key
        TokenCreateTransaction tokenCreateSign = tokenCreateTx.sign(treasuryKey);

        // Submit the transaction
        TransactionResponse tokenCreateSubmit = tokenCreateSign.execute(client);

        // Get the transaction receipt
        TransactionReceipt tokenCreateRx = tokenCreateSubmit.getReceipt(client);

        // Get the token ID
        TokenId tokenId = tokenCreateRx.tokenId;

        // Log the token ID to the console
        System.out.println("Created token with ID: " + tokenId);

        // TOKEN ASSOCIATION WITH ALICE's ACCOUNT
        TokenAssociateTransaction associateAliceTx = new TokenAssociateTransaction()
                .setAccountId(aliceAccountId)
                .setTokenIds(Collections.singletonList(tokenId))
                .freezeWith(client)
                .sign(aliceKey);

        // Submit the transaction
        TransactionResponse associateAliceTxSubmit = associateAliceTx.execute(client);

        // Get the receipt of the transaction
        TransactionReceipt associateAliceRx = associateAliceTxSubmit.getReceipt(client);

        // Get the transaction status
        System.out.println("Token association with Alice's account: " + associateAliceRx.status);

        // BALANCE CHECK
        AccountBalance balanceCheckTreasury =
                new AccountBalanceQuery().setAccountId(treasuryId).execute(client);
        System.out.println(" Treasury balance: " + balanceCheckTreasury.tokens
                + " units of token ID" + tokenId);
        AccountBalance balanceCheckAlice =
                new AccountBalanceQuery().setAccountId(aliceAccountId).execute(client);
        System.out.println(
                "Alice's balance: " + balanceCheckAlice.tokens + " units of token ID " + tokenId);

        // TRANSFER STABLECOIN FROM TREASURY TO ALICE
        TransferTransaction tokenTransferTx = new TransferTransaction()
                .addTokenTransfer(tokenId, treasuryId, -2500)
                .addTokenTransfer(tokenId, aliceAccountId, 2500)
                .freezeWith(client)
                .sign(treasuryKey);

        // SUBMIT THE TRANSACTION
        TransactionResponse tokenTransferSubmit = tokenTransferTx.execute(client);

        // GET THE RECEIPT OF THE TRANSACTION
        TransactionReceipt tokenTransferRx = tokenTransferSubmit.getReceipt(client);

        // LOG THE TRANSACTION STATUS
        System.out.println("Stablecoin transfer from Treasury to Alice: " + tokenTransferRx.status);

        // BALANCE CHECK
        AccountBalance balanceCheckTreasury2 =
                new AccountBalanceQuery().setAccountId(treasuryId).execute(client);
        System.out.println("Treasury balance " + balanceCheckTreasury2.tokens
                + " units of token ID " + tokenId);
        AccountBalance balanceCheckAlice2 =
                new AccountBalanceQuery().setAccountId(aliceAccountId).execute(client);
        System.out.println(
                "Alice's balance: " + balanceCheckAlice2.tokens + " units of token ID " + tokenId);
    }
}
