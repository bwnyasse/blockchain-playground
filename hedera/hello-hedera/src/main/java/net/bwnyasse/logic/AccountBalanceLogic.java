package net.bwnyasse.logic;

import java.util.concurrent.TimeoutException;
import com.hedera.hashgraph.sdk.AccountBalance;
import com.hedera.hashgraph.sdk.AccountBalanceQuery;
import com.hedera.hashgraph.sdk.PrecheckStatusException;
import net.bwnyasse.BlockchainPlaygroundHederaCommon;


public class AccountBalanceLogic {

    public static void exec() throws TimeoutException, PrecheckStatusException {
        // Check the new account's balance
        AccountBalance accountBalance = new AccountBalanceQuery()
                .setAccountId(BlockchainPlaygroundHederaCommon.getAccountId())
                .execute(BlockchainPlaygroundHederaCommon.getHederaClientForTestnet());

                    
        System.out.println("My origin account balance is: " + accountBalance.hbars);
    }
}
