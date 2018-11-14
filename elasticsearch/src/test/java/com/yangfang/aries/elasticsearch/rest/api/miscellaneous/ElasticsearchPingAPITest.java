package com.yangfang.aries.elasticsearch.rest.api.miscellaneous;

import com.yangfang.aries.elasticsearch.SpringbootApplicationTests;
import com.yangfang.aries.elasticsearch.config.BaseConfiguration;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * ping api test
 *
 * @author 幽明
 * @serial 2018/11/14
 */
class ElasticsearchPingAPITest extends SpringbootApplicationTests {

    @Test
    @DisplayName("ping api")
    void testPing() throws IOException {
        RestHighLevelClient client = BaseConfiguration.restHighLevelClient();
        boolean response = client.ping(RequestOptions.DEFAULT);
        Assert.assertTrue(response);
    }
}
