package com.yangfang.aries.elasticsearch;

import com.yangfang.aries.elasticsearch.context.Configuration;
import com.yangfang.aries.elasticsearch.enums.URI;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Objects;

/**
 * elasticsearch API test
 *
 * @author 幽明
 * @serial 2018/11/13
 */
@Slf4j
class ElasticsearchJavaAPITest {

    private static final GetRequest GET_REQUEST = new GetRequest(Configuration.DEFAULT_ELASTIC_INDEX,
            Configuration.DEFAULT_ELASTIC_TYPE, Configuration.DEFAULT_ELASTICSEARCH_DOC_ID);

    @Test
    @DisplayName("建索")
    void testIndex() {
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

    @Test
    @DisplayName("同步get")
    void testGet() {
        try (RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(Configuration.DEFAULT_ELASTICSEARCH_REST_HOSTNAME,
                                Configuration.DEFAULT_ELASTICSEARCH_REST_PORT,
                                URI.HTTP.getCode())))) {

            GetResponse response = client.get(GET_REQUEST, RequestOptions.DEFAULT);
            log.info("{}", response);
        } catch (IOException e) {
            log.warn("Failed to build a RestHighLevelClient, cause:", e);
        }
    }

    @Test
    @DisplayName("异步get")
    void testAsynchronousGet() {
        try (RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(Configuration.DEFAULT_ELASTICSEARCH_REST_HOSTNAME,
                                Configuration.DEFAULT_ELASTICSEARCH_REST_PORT,
                                URI.HTTP.getCode())))) {

            ActionListener<GetResponse> listener = new ActionListener<GetResponse>() {
                @Override
                public void onResponse(GetResponse getResponse) {
                    log.info("{}", getResponse);
                }

                @Override
                public void onFailure(Exception e) {
                    log.warn("Failed to get", e);
                }
            };

            client.getAsync(GET_REQUEST, RequestOptions.DEFAULT, listener);
            Thread.sleep(5000);
        } catch (IOException e) {
            log.warn("Failed to build a RestHighLevelClient, cause:", e);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("访问不存在的索引")
    void testGetWithNotExistIndex() throws IOException {
        RestHighLevelClient client = BaseConfiguration.restHighLevelClient();
        Assert.assertNotNull(client);

        GetRequest request = new GetRequest(Configuration.NOT_EXIST_ELASTIC_INDEX,
                Configuration.DEFAULT_ELASTIC_TYPE, Configuration.DEFAULT_ELASTICSEARCH_DOC_ID);
        GetResponse response = null;
        try {
            response = client.get(request, RequestOptions.DEFAULT);
        } catch (ElasticsearchStatusException ese) {
            // returns a rest status
            RestStatus restStatus = ese.status();
            log.warn("index[{}] is not exist", Configuration.NOT_EXIST_ELASTIC_INDEX, ese);
            return;
        }

        if (Objects.nonNull(response) && response.isExists()) {
            log.info("{}", response);
        } else {
            log.warn("index[{}] is not exist", Configuration.NOT_EXIST_ELASTIC_INDEX);
        }
    }

    /**
     * 访问ES时可以在请求中指定version，当ES服务器中的文档版本与请求中的版本不一致时，将会抛出ElasticsearchStatusException，
     * 异常中记录的状态即是RestStatus.CONFLICT
     */
    @Test
    @DisplayName("版本冲突")
    void testGetInConflict() throws IOException {
        RestHighLevelClient client = BaseConfiguration.restHighLevelClient();
        Assert.assertNotNull(client);

        GetResponse response;
        try {
            response = client.get(GET_REQUEST.version(2), RequestOptions.DEFAULT);
        } catch (ElasticsearchStatusException ese) {
            if (ese.status() == RestStatus.CONFLICT) {
                log.warn("request conflict", ese);
            }
            return;
        }

        if (Objects.nonNull(response) && response.isExists()) {
            log.info("{}", response);
        }
    }

    @Test
    @DisplayName("索引存在与否")
    void testIfIndexExist() throws IOException {
        RestHighLevelClient client = BaseConfiguration.restHighLevelClient();
        Assert.assertNotNull(client);

        boolean exists;
        try {
            exists = client.exists(GET_REQUEST, RequestOptions.DEFAULT);
        } catch (ElasticsearchStatusException ese) {
            log.warn("exception occurred when invoke a request", ese);
            return;
        }

        Assert.assertTrue(exists);

        GetRequest request = new GetRequest(Configuration.NOT_EXIST_ELASTIC_INDEX,
                Configuration.DEFAULT_ELASTIC_TYPE, Configuration.DEFAULT_ELASTICSEARCH_DOC_ID);
        exists = client.exists(request, RequestOptions.DEFAULT);
        Assert.assertFalse(exists);
    }

}
