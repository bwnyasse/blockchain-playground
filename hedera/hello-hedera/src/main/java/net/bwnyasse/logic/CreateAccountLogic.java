package net.bwnyasse.logic;

import java.util.concurrent.TimeoutException;
import com.hedera.hashgraph.sdk.AccountBalance;
import com.hedera.hashgraph.sdk.AccountBalanceQuery;
import com.hedera.hashgraph.sdk.AccountCreateTransaction;
import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Client;
import com.hedera.hashgraph.sdk.Hbar;
import com.hedera.hashgraph.sdk.PrecheckStatusException;
import com.hedera.hashgraph.sdk.PrivateKey;
import com.hedera.hashgraph.sdk.PublicKey;
import com.hedera.hashgraph.sdk.ReceiptStatusException;
import com.hedera.hashgraph.sdk.TransactionResponse;
import net.bwnyasse.common.Common;

public class CreateAccountLogic {

    public static AccountId singleCreateNewAccount()
            throws TimeoutException, PrecheckStatusException, ReceiptStatusException {
        // Grab your Hedera testnet account ID and private key
        // AccountId myAccountId = AccountId.fromString(Dotenv.load().get("MY_ACCOUNT_ID"));
        // PrivateKey myPrivateKey =
        // PrivateKey.fromString(Dotenv.load().get("MY_PRIVATE_KEY"));

        // Create your Hedera testnet client
        // Client client = Client.forTestnet();
        // client.setOperator(Common.getAccountId(), Common.getMyPrivateKey());
        Client client = Common.getHederaClientForTestnet();

        // Generate a new key pair
        PrivateKey newAccountPrivateKey = PrivateKey.generateED25519();
        PublicKey newAccountPublicKey = newAccountPrivateKey.getPublicKey();

        // Create new account and assign the public key
        TransactionResponse newAccount = new AccountCreateTransaction()
                .setKey(newAccountPublicKey)
                .setInitialBalance(Hbar.fromTinybars(1000))
                .execute(client);

        // Get the new account ID
        AccountId newAccountId = newAccount.getReceipt(client).accountId;

        return newAccountId;

    }

    public static AccountId exec()
            throws TimeoutException, PrecheckStatusException, ReceiptStatusException {

        System.out.println("------------------------------------");

        // Get the new account ID
        AccountId newAccountId = singleCreateNewAccount();

        // Log the account ID
        System.out.println("The new account ID is: " + newAccountId);

        Client client = Common.getHederaClientForTestnet();

        // Check the new account's balance
        AccountBalance accountBalance = new AccountBalanceQuery()
                .setAccountId(newAccountId)
                .execute(client);

        System.out.println("The new account balance is: " + accountBalance.hbars);

        return newAccountId;
    }
}
