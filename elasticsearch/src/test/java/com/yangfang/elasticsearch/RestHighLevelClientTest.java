package com.yangfang.elasticsearch;

import com.yangfang.elasticsearch.context.Configuration;
import com.yangfang.elasticsearch.enums.URI;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.Test;

import java.io.IOException;

/**
 * springboot test
 *
 * @author 幽明
 * @serial 2018/11/13
 */
@Slf4j
public class RestHighLevelClientTest extends SpringbootApplicationTests {

    @Test
    public void test() {
        try (RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(Configuration.DEFAULT_ELASTICSEARCH_REST_HOSTNAME,
                                Configuration.DEFAULT_ELASTICSEARCH_REST_PORT,
                                URI.HTTP.getCode())))) {

            IndexRequest request = new IndexRequest(Configuration.DEFAULT_ELASTIC_INDEX,
                    Configuration.DEFAULT_ELASTIC_TYPE,
                    Configuration.DEFAULT_ELASTICSEARCH_DOC_ID
            ).source(EntityFactory.getJsonString(), XContentType.JSON);
            request.timeout(TimeValue.timeValueSeconds(1));

            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
            log.info("{}", response);
        } catch (IOException e) {
            log.warn("Failed to build a RestHighLevelClient, cause:", e);
        }
    }
}
