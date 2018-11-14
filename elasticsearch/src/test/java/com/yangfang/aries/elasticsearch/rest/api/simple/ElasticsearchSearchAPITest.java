package com.yangfang.aries.elasticsearch.rest.api.simple;

import com.yangfang.aries.elasticsearch.SpringbootApplicationTests;
import com.yangfang.aries.elasticsearch.config.BaseConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.avg.Avg;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * search api test case
 *
 * @author 幽明
 * @serial 2018/11/14
 */
@Slf4j
class ElasticsearchSearchAPITest extends SpringbootApplicationTests {

    @Test
    @DisplayName("测试search API")
    void testSearch() {
        RestHighLevelClient client = BaseConfiguration.restHighLevelClient();
        SearchRequest request = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        request.source(searchSourceBuilder);

        SearchResponse response = null;
        try {
            response = client.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Aggregations aggregations = response.getAggregations();
        Terms byCompanyAggregation = aggregations.get("by_company");
        Terms.Bucket elasticBucket = byCompanyAggregation.getBucketByKey("Elastic");
        Avg averageAge = elasticBucket.getAggregations().get("average_age");
        double avg = averageAge.getValue();
    }

    @Test
    @DisplayName("multi search API")
    void testMultiSearch() {
        RestHighLevelClient client = BaseConfiguration.restHighLevelClient();
        MultiSearchRequest request = new MultiSearchRequest();
        SearchRequest firstSearchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("user", "kimchy"));
        firstSearchRequest.source(searchSourceBuilder);
        request.add(firstSearchRequest);

        SearchRequest secondSearchRequest = new SearchRequest();
        searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("user", "luca"));
        secondSearchRequest.source(searchSourceBuilder);
        request.add(secondSearchRequest);

        MultiSearchResponse response;
        try {
            response = client.msearch(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            return;
        }

        MultiSearchResponse.Item firstResponse = response.getResponses()[0];
        Assert.assertNull(firstResponse.getFailure());
        SearchResponse searchResponse = firstResponse.getResponse();
        Assert.assertEquals(4, searchResponse.getHits().getTotalHits());
        MultiSearchResponse.Item secondResponse = response.getResponses()[1];
        Assert.assertNull(secondResponse.getFailure());
        searchResponse = secondResponse.getResponse();
        Assert.assertEquals(1, searchResponse.getHits().getTotalHits());
    }

}
