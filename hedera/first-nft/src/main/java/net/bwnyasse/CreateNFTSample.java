package net.bwnyasse;

import java.util.Collections;
import java.util.concurrent.TimeoutException;
import com.hedera.hashgraph.sdk.*;

public class CreateNFTSample {

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
        PublicKey supplyPublicKey = supplyKey.getPublicKey();

        // Create the NFT
        TokenCreateTransaction nftCreate = new TokenCreateTransaction()
                .setTokenName("diploma")
                .setTokenSymbol("GRAD")
                .setTokenType(TokenType.NON_FUNGIBLE_UNIQUE)
                .setDecimals(0)
                .setInitialSupply(0)
                .setTreasuryAccountId(treasuryId)
                .setSupplyType(TokenSupplyType.FINITE)
                .setMaxSupply(250)
                .setSupplyKey(supplyKey)
                .freezeWith(client);

        // Sign the transaction with the treasury key
        TokenCreateTransaction nftCreateTxSign = nftCreate.sign(treasuryKey);

        // Submit the transaction to a Hedera network
        TransactionResponse nftCreateSubmit = nftCreateTxSign.execute(client);

        // Get the transaction receipt
        TransactionReceipt nftCreateRx = nftCreateSubmit.getReceipt(client);

        // Get the token ID
        TokenId tokenId = nftCreateRx.tokenId;

        // Log the token ID
        System.out.println("Created NFT with token ID " + tokenId);

        // IPFS CONTENT IDENTIFIERS FOR WHICH WE WILL CREATE NFT
        String CID = ("QmTzWcVfk88JRqjTpVwHzBeULRTNzHY7mnBSG42CpwHmPa");

        // MINT NEW NFT
        TokenMintTransaction mintTx = new TokenMintTransaction()
                .setTokenId(tokenId)
                .addMetadata(CID.getBytes())
                .freezeWith(client);

        // Sign with the supply key
        TokenMintTransaction mintTxSign = mintTx.sign(supplyKey);

        // Submit the transaction to a Hedera network
        TransactionResponse mintTxSubmit = mintTxSign.execute(client);

        // Get the transaction receipt
        TransactionReceipt mintRx = mintTxSubmit.getReceipt(client);

        // Log the serial number
        System.out.println("Created NFT " + tokenId + "with serial: " + mintRx.serials);

        // Create the associate transaction and sign with Alice's key
        TokenAssociateTransaction associateAliceTx = new TokenAssociateTransaction()
                .setAccountId(aliceAccountId)
                .setTokenIds(Collections.singletonList(tokenId))
                .freezeWith(client)
                .sign(aliceKey);

        // Submit the transaction to a Hedera network
        TransactionResponse associateAliceTxSubmit = associateAliceTx.execute(client);

        // Get the transaction receipt
        TransactionReceipt associateAliceRx = associateAliceTxSubmit.getReceipt(client);

        // Confirm the transaction was successful
        System.out.println("NFT association with Alice's account: " + associateAliceRx.status);

        // Check the balance before the NFT transfer for the treasury account
        AccountBalance balanceCheckTreasury =
                new AccountBalanceQuery().setAccountId(treasuryId).execute(client);
        System.out.println(
                "Treasury balance: " + balanceCheckTreasury.tokens + "NFTs of ID " + tokenId);

        // Check the balance before the NFT transfer for Alice's account
        AccountBalance balanceCheckAlice =
                new AccountBalanceQuery().setAccountId(aliceAccountId).execute(client);
        System.out
                .println("Alice's balance: " + balanceCheckAlice.tokens + "NFTs of ID " + tokenId);

        // Transfer NFT from treasury to Alice
        // Sign with the treasury key to authorize the transfer
        TransferTransaction tokenTransferTx = new TransferTransaction()
                .addNftTransfer(new NftId(tokenId, 1), treasuryId, aliceAccountId)
                .freezeWith(client)
                .sign(treasuryKey);

        TransactionResponse tokenTransferSubmit = tokenTransferTx.execute(client);
        TransactionReceipt tokenTransferRx = tokenTransferSubmit.getReceipt(client);

        System.out.println("NFT transfer from Treasury to Alice: " + tokenTransferRx.status);

        // Check the balance for the treasury account after the transfer
        AccountBalance balanceCheckTreasury2 =
                new AccountBalanceQuery().setAccountId(treasuryId).execute(client);
        System.out.println(
                "Treasury balance: " + balanceCheckTreasury2.tokens + "NFTs of ID " + tokenId);

        // Check the balance for Alice's account after the transfer
        AccountBalance balanceCheckAlice2 =
                new AccountBalanceQuery().setAccountId(aliceAccountId).execute(client);
        System.out
                .println("Alice's balance: " + balanceCheckAlice2.tokens + "NFTs of ID " + tokenId);


        // indicates successful termination
        Runtime.getRuntime().exit(0);
    }
}
