package net.bwnyasse;

import java.util.concurrent.TimeoutException;
import com.hedera.hashgraph.sdk.PrecheckStatusException;
import com.hedera.hashgraph.sdk.ReceiptStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.bwnyasse.logic.CreateAccountLogic;

public class CreateAccountSample {

        private static Logger LOGGER = LoggerFactory.getLogger(CreateAccountSample.class);

        public static void main(String[] args)
                        throws TimeoutException, PrecheckStatusException, ReceiptStatusException {

                LOGGER.info(" [START] Calling the account creation logic");

                // Call the logic
                CreateAccountLogic.exec();

                LOGGER.info(" [END] Calling the account creation logic");

                // indicates successful termination
                Runtime.getRuntime().exit(0);
        }

}
