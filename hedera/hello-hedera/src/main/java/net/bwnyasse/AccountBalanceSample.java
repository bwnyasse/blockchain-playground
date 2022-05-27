package net.bwnyasse;

import java.util.concurrent.TimeoutException;
import com.hedera.hashgraph.sdk.PrecheckStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.bwnyasse.logic.AccountBalanceLogic;

public class AccountBalanceSample {

    private static Logger LOGGER = LoggerFactory.getLogger(AccountBalanceSample.class);

    public static void main(String[] args) throws TimeoutException, PrecheckStatusException {

        LOGGER.info(" [START] Calling the account balance logic");

        AccountBalanceLogic.exec();

        LOGGER.info(" [END] Calling the account balance logic");

        // indicates successful termination
        Runtime.getRuntime().exit(0);
    }
}
