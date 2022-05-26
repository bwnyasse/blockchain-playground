package net.bwnyasse;

import java.util.concurrent.TimeoutException;
import com.hedera.hashgraph.sdk.PrecheckStatusException;
import net.bwnyasse.logic.AccountBalanceLogic;

public class AccountBalanceSample {

    public static void main(String[] args) throws TimeoutException, PrecheckStatusException {
        AccountBalanceLogic.exec();
    }
}
