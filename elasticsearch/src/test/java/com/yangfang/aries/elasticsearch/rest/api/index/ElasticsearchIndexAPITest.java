package com.yangfang.aries.elasticsearch.rest.api.index;

import com.yangfang.aries.elasticsearch.SpringbootApplicationTests;
import com.yangfang.aries.elasticsearch.config.BaseConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.rest.RestStatus;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * indices api test
 *
 * @author 幽明
 * @serial 2018/11/14
 */
@Slf4j
class ElasticsearchIndexAPITest extends SpringbootApplicationTests {

    private static final String INDEX_NAME = "create_index_test";

    @Test
    @DisplayName("create a index")
    void testCreateIndex() throws IOException {
        RestHighLevelClient client = BaseConfiguration.restHighLevelClient();

        GetIndexRequest getIndexRequest = new GetIndexRequest();
        getIndexRequest.indices(INDEX_NAME);
        if (client.indices().exists(getIndexRequest, RequestOptions.DEFAULT)) {
            DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest(INDEX_NAME);

            DeleteIndexResponse deleteIndexResponse = client.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
            log.info("{}", deleteIndexResponse);
            Assert.assertEquals(INDEX_NAME, deleteIndexRequest.indices()[0]);
        }

        CreateIndexRequest request = new CreateIndexRequest(INDEX_NAME);

        // index settings
        request.settings(Settings.builder()
                .put("index.number_of_shards", 3)
                .put("index.number_of_replicas", 2)
        );

        // build a mapping
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.startObject("_doc");
            {
                builder.startObject("properties");
                {
                    builder.startObject("message");
                    {
                        builder.field("type", "text");
                    }
                    builder.endObject();
                }
                builder.endObject();
            }
            builder.endObject();
        }
        builder.endObject();
        // set mapping
        request.mapping("_doc", builder);

        CreateIndexResponse createIndexResponse = null;
        try {
            createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        } catch (ElasticsearchException e) {

        }

        log.info("{}", createIndexResponse);
        Assert.assertNotNull(createIndexResponse);
        Assert.assertTrue(createIndexResponse.isAcknowledged());
    }

    @Test
    @DisplayName("delete a index")
    void testDeleteIndex() throws IOException {
        RestHighLevelClient client = BaseConfiguration.restHighLevelClient();

        DeleteIndexRequest request = new DeleteIndexRequest(INDEX_NAME);
        DeleteIndexResponse response = null;
        try {
            response = client.indices().delete(request, RequestOptions.DEFAULT);
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.NOT_FOUND) {
                return;
            }
        }

        log.info("{}", response);
        Assert.assertNotNull(response);
        Assert.assertTrue(response.isAcknowledged());

    }

}
