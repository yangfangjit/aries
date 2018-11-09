package com.yangfang.elasticsearch;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;

/**
 * elasticsearch 测试
 *
 * @author 幽明
 * @serial 2018/11/8
 */
@Slf4j
public class ConnectElasticViaTransportClient {

    public static void main(String[] args) {
        try (TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(Configuration.defaultHost(), Configuration.DEFAULT_ELASTICSEARCH_PORT))) {
            log.info("Elasticsearch node num: {}", client.listedNodes().size());

            IndexResponse response = client.prepareIndex(Configuration.DEFAULT_ELASTIC_INDEX,
                    Configuration.DEFAULT_ELASTIC_TYPE,
                    Configuration.DEFAULT_ELASTICSEARCH_DOC_ID
            ).setSource(EntityFactory.getJsonString(), XContentType.JSON).get();

            log.info(response.toString());
        } catch (IOException e) {
            log.warn("w", e);
        }
    }
}
