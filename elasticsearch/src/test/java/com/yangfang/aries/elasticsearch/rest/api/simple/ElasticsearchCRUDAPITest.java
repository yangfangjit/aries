package com.yangfang.aries.elasticsearch.rest.api.simple;

import com.google.common.collect.Lists;
import com.jayway.awaitility.Awaitility;
import com.yangfang.aries.elasticsearch.EntityFactory;
import com.yangfang.aries.elasticsearch.config.BaseConfiguration;
import com.yangfang.aries.elasticsearch.context.Configuration;
import com.yangfang.aries.elasticsearch.enums.URI;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * elasticsearch API test
 *
 * @author 幽明
 * @serial 2018/11/13
 */
@Slf4j
class ElasticsearchSimpleAPITest {

    private static final GetRequest GET_REQUEST = new GetRequest(Configuration.DEFAULT_ELASTIC_INDEX,
            Configuration.DEFAULT_ELASTIC_TYPE, Configuration.DEFAULT_ELASTICSEARCH_DOC_ID);

    private static final DeleteRequest DELETE_REQUEST = new DeleteRequest(Configuration.DEFAULT_ELASTIC_INDEX,
            Configuration.DEFAULT_ELASTIC_TYPE, Configuration.DEFAULT_ELASTICSEARCH_DOC_ID);

    private static final UpdateRequest UPDATE_REQUEST = new UpdateRequest(Configuration.DEFAULT_ELASTIC_INDEX,
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
        List<GetResponse> responses = Lists.newArrayListWithCapacity(1);
        try (RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(Configuration.DEFAULT_ELASTICSEARCH_REST_HOSTNAME,
                                Configuration.DEFAULT_ELASTICSEARCH_REST_PORT,
                                URI.HTTP.getCode())))) {

            client.getAsync(GET_REQUEST, RequestOptions.DEFAULT, new ActionListener<GetResponse>() {
                @Override
                public void onResponse(GetResponse getResponse) {
                    responses.add(getResponse);
                }

                @Override
                public void onFailure(Exception e) {
                    log.warn("Failed to get", e);
                }
            });

            long start = System.currentTimeMillis();
            // handle wait in asynchronous case
            Awaitility.await().atMost(5, TimeUnit.MINUTES).until(() -> !responses.isEmpty());
            log.info("Asynchronous get costs {}ms", System.currentTimeMillis() - start);

            // Awaitility.await().atMost(5, TimeUnit.MINUTES).

            if (!responses.isEmpty()) {
                log.info("{}", responses.get(0));
            }
        } catch (IOException e) {
            log.warn("Failed to build a RestHighLevelClient, cause:", e);
        }
    }

    @Test
    @DisplayName("访问不存在的索引")
    void testGetWithNotExistIndex() throws IOException {
        RestHighLevelClient client = BaseConfiguration.restHighLevelClient();
        Assert.assertNotNull(client);

        GetRequest request = new GetRequest(Configuration.NOT_EXIST_ELASTIC_INDEX,
                Configuration.DEFAULT_ELASTIC_TYPE, Configuration.DEFAULT_ELASTICSEARCH_DOC_ID);
        GetResponse response;
        try {
            response = client.get(request, RequestOptions.DEFAULT);
        } catch (ElasticsearchStatusException ese) {
            // returns a rest status
            // RestStatus restStatus = ese.status();
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
    @DisplayName("get版本冲突")
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


    @Test
    @DisplayName("同步delete")
    void testDelete() throws IOException {
        RestHighLevelClient client = BaseConfiguration.restHighLevelClient();
        Assert.assertNotNull(client);

        DeleteResponse response = null;
        try {
            response = client.delete(DELETE_REQUEST, RequestOptions.DEFAULT);
        } catch (ElasticsearchStatusException ese) {
            log.warn("exception occurred when invoke a request", ese);
        }

        if (Objects.nonNull(response)) {
            log.info("{}", response);
        }
    }

    @Test
    @DisplayName("同步update")
    void testUpdate() throws IOException {
        RestHighLevelClient client = BaseConfiguration.restHighLevelClient();
        Assert.assertNotNull(client);

        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.timeField("updated", new Date());
            builder.field("reason", "daily update");
        }
        builder.endObject();
        UpdateRequest request = new UpdateRequest("posts", "doc", "1")
                .doc(builder);

        UpdateResponse response = null;
        try {
            response = client.update(request, RequestOptions.DEFAULT);
        } catch (ElasticsearchStatusException ese) {
            log.warn("exception occurred when invoke a request", ese);
        }

        if (Objects.nonNull(response)) {
            log.info("{}", response);
        }
    }

    @Test
    @DisplayName("bulk - multi request")
    void testBulk() throws IOException {
        RestHighLevelClient client = BaseConfiguration.restHighLevelClient();
        Assert.assertNotNull(client);

        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.timeField("updated", new Date());
            builder.field("reason", "daily update");
        }
        builder.endObject();
        UpdateRequest updateRequest = new UpdateRequest("posts", "doc", "1")
                .doc(builder);

        BulkRequest request = new BulkRequest();
        request.add(updateRequest);


        UpdateResponse response = null;
        try {
            response = client.update(UPDATE_REQUEST.doc(builder), RequestOptions.DEFAULT);
        } catch (ElasticsearchStatusException ese) {
            log.warn("exception occurred when invoke a request", ese);
        }

        if (Objects.nonNull(response)) {
            log.info("{}", response);
        }
    }

}
