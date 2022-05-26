package net.bwnyasse.logic;

import java.util.concurrent.TimeoutException;
import com.hedera.hashgraph.sdk.AccountBalance;
import com.hedera.hashgraph.sdk.AccountBalanceQuery;
import com.hedera.hashgraph.sdk.PrecheckStatusException;
import net.bwnyasse.common.Common;


public class AccountBalanceLogic {

    public static void exec() throws TimeoutException, PrecheckStatusException {
        // Check the new account's balance
        AccountBalance accountBalance = new AccountBalanceQuery()
                .setAccountId(Common.getAccountId())
                .execute(Common.getHederaClientForTestnet());

        System.out.println("My origin account balance is: " + accountBalance.hbars);
    }
}
