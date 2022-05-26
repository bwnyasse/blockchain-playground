package net.bwnyasse;

import java.util.concurrent.TimeoutException;
import com.hedera.hashgraph.sdk.AccountBalance;
import com.hedera.hashgraph.sdk.AccountBalanceQuery;
import com.hedera.hashgraph.sdk.AccountId;
import com.hedera.hashgraph.sdk.Client;
import com.hedera.hashgraph.sdk.Hbar;
import com.hedera.hashgraph.sdk.PrecheckStatusException;
import com.hedera.hashgraph.sdk.ReceiptStatusException;
import com.hedera.hashgraph.sdk.TransactionResponse;
import com.hedera.hashgraph.sdk.TransferTransaction;
import net.bwnyasse.common.Common;
import net.bwnyasse.logic.AccountBalanceLogic;
import net.bwnyasse.logic.CreateAccountLogic;

public class TransferHbarSample {

    public static void main(String[] args)
            throws TimeoutException, PrecheckStatusException, ReceiptStatusException {
        //
        AccountId myAccountId = Common.getAccountId();
        AccountId newAccountId = CreateAccountLogic.exec();
        Client client = Common.getHederaClientForTestnet();

        // Transfer hbar
        TransactionResponse sendHbar = new TransferTransaction()
                .addHbarTransfer(myAccountId, Hbar.fromTinybars(-1000)) // Sending account
                .addHbarTransfer(newAccountId, Hbar.fromTinybars(1000)) // Receiving account
                .execute(client);

        // Verify the transfer transaction reached consensus
        System.out.println("The transfer transaction was: " + sendHbar.getReceipt(client).status);

        // Request the cost of the query
        Hbar queryCost = new AccountBalanceQuery()
                .setAccountId(newAccountId)
                .getCost(client);

        System.out.println("The cost of this query is: " + queryCost);

        // Check the new account's balance
        AccountBalance accountBalanceNew = new AccountBalanceQuery()
                .setAccountId(newAccountId)
                .execute(client);

        System.out.println("The new account balance is: " + accountBalanceNew.hbars);
                
        // Just to check my actual account balance
        AccountBalanceLogic.exec();
        
        // indicates successful termination
        Runtime.getRuntime().exit(0);
    }
}
