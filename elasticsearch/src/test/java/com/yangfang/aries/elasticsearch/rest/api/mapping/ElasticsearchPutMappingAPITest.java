package com.yangfang.aries.elasticsearch.rest.api.mapping;

import com.yangfang.aries.elasticsearch.SpringbootApplicationTests;
import com.yangfang.aries.elasticsearch.config.BaseConfiguration;
import com.yangfang.aries.elasticsearch.context.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.mapping.get.GetFieldMappingsRequest;
import org.elasticsearch.action.admin.indices.mapping.get.GetFieldMappingsResponse;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsRequest;
import org.elasticsearch.action.admin.indices.mapping.get.GetMappingsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

/**
 * mapping api test
 *
 * @author 幽明
 * @serial 2018/11/14
 */
@Slf4j
class ElasticsearchPutMappingAPITest extends SpringbootApplicationTests {

    @Test
    @DisplayName("put mapping api")
    void testPutMapping() throws IOException {
        RestHighLevelClient client = BaseConfiguration.restHighLevelClient();

        PutMappingRequest request = new PutMappingRequest(Configuration.DEFAULT_ELASTIC_INDEX);
        request.type(Configuration.DEFAULT_ELASTIC_TYPE);

        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
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
        request.source(builder);

        PutMappingResponse putMappingResponse = client.indices().putMapping(request, RequestOptions.DEFAULT);
        Assert.assertNotNull(putMappingResponse);
        Assert.assertTrue(putMappingResponse.isAcknowledged());
    }

    @Test
    @DisplayName("get mapping api")
    void testGetMapping() throws IOException {
        RestHighLevelClient client = BaseConfiguration.restHighLevelClient();
        GetMappingsRequest request = new GetMappingsRequest();
        request.indices(Configuration.DEFAULT_ELASTIC_INDEX);
        request.types(Configuration.DEFAULT_ELASTIC_TYPE);
        // optional arguments
        request.masterNodeTimeout(TimeValue.timeValueMinutes(1));
        GetMappingsResponse getMappingResponse = client.indices().getMapping(request, RequestOptions.DEFAULT);
        log.info("{}", getMappingResponse);
        Assert.assertNotNull(getMappingResponse);

        ImmutableOpenMap<String, ImmutableOpenMap<String, MappingMetaData>> allMappings = getMappingResponse.mappings();
        MappingMetaData typeMapping = allMappings.get(Configuration.DEFAULT_ELASTIC_INDEX).get(Configuration.DEFAULT_ELASTIC_TYPE);
        Map<String, Object> mapping = typeMapping.sourceAsMap();
        Assert.assertNotNull(mapping);
    }


    @Test
    @DisplayName("get field mapping")
    void testGetFieldMapping() throws IOException {
        RestHighLevelClient client = BaseConfiguration.restHighLevelClient();
        GetFieldMappingsRequest request = new GetFieldMappingsRequest();
        request.indices(Configuration.DEFAULT_ELASTIC_INDEX);
        request.types(Configuration.DEFAULT_ELASTIC_TYPE);
        request.fields("message", "timestamp");

        GetFieldMappingsResponse response = client.indices().getFieldMapping(request, RequestOptions.DEFAULT);
        Assert.assertNotNull(request);

        Map<String, Map<String, Map<String, GetFieldMappingsResponse.FieldMappingMetaData>>> mappings = response.mappings();
        Map<String, GetFieldMappingsResponse.FieldMappingMetaData> typeMappings =
                mappings.get(Configuration.DEFAULT_ELASTIC_INDEX).get(Configuration.DEFAULT_ELASTIC_TYPE);
        GetFieldMappingsResponse.FieldMappingMetaData metaData = typeMappings.get("message");

        String fullName = metaData.fullName();
        log.info("full name is {}", fullName);
        Assert.assertNotNull(fullName);

        Map<String, Object> source = metaData.sourceAsMap();
        log.info("source is {}", source);
        Assert.assertNotNull(source);

    }

}
