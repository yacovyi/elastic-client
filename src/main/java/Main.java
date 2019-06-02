import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {

    private static final String CLUSTER_NAME = "wscns_ci";

    public static void main(String[] args) throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("cluster.name", CLUSTER_NAME).build();
        TransportClient client = new PreBuiltTransportClient(settings);

        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(args[0]), 9300));

        ImmutableOpenMap<String, IndexMetaData> indices = client.admin().cluster()
            .prepareState().get().getState()
            .getMetaData().getIndices();
        System.out.println("Host " + args[0] + ", Indices - " + indices.size()) ;
        client.close();

    }
}
