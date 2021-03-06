package net.bwnyasse;

import com.hedera.hashgraph.sdk.PrivateKey;
import java.util.Objects;
import com.hedera.hashgraph.sdk.AccountId;
import io.github.cdimascio.dotenv.Dotenv;
import com.hedera.hashgraph.sdk.Client;

public class BlockchainPlaygroundHederaCommon {

    public static Client client;

    public static AccountId getAccountId() {
        return AccountId.fromString(Objects.requireNonNull(Dotenv.load().get("MY_ACCOUNT_ID")));
    }

    public static PrivateKey getMyPrivateKey() {
        return PrivateKey.fromString(Objects.requireNonNull(Dotenv.load().get("MY_PRIVATE_KEY")));
    }

    public static Client getHederaClientForTestnet() {
        if (client != null) {
            return client;
        }
        client = Client.forTestnet();
        client.setOperator(getAccountId(), getMyPrivateKey());
        return client;
    }

}
