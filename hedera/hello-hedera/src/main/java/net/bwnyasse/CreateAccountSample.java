package net.bwnyasse;

import java.util.concurrent.TimeoutException;
import com.hedera.hashgraph.sdk.PrecheckStatusException;
import com.hedera.hashgraph.sdk.ReceiptStatusException;
import net.bwnyasse.logic.CreateAccountLogic;

public class CreateAccountSample {

        public static void main(String[] args)
                        throws TimeoutException, PrecheckStatusException, ReceiptStatusException {
                // Call the logic
                CreateAccountLogic.exec();

                // indicates successful termination
                Runtime.getRuntime().exit(0);
        }

}
