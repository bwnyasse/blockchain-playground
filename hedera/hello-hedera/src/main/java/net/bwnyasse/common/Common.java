package net.bwnyasse.common;

import com.hedera.hashgraph.sdk.PrivateKey;
import com.hedera.hashgraph.sdk.AccountId;
import io.github.cdimascio.dotenv.Dotenv;
import com.hedera.hashgraph.sdk.Client;

public class Common {

    public static Client client;

    public static AccountId getAccountId() {
        return AccountId.fromString(Dotenv.load().get("MY_ACCOUNT_ID"));
    }

    public static PrivateKey getMyPrivateKey() {
        return PrivateKey.fromString(Dotenv.load().get("MY_PRIVATE_KEY"));
    }

    public static Client getHederaClientForTestnet() {
        if (client != null) {
            return client;
        }
        client = Client.forTestnet();
        client.setOperator(Common.getAccountId(), Common.getMyPrivateKey());
        return client;
    }
}
