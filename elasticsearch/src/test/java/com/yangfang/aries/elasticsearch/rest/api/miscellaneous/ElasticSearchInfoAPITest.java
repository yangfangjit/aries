package com.yangfang.aries.elasticsearch.rest.api.miscellaneous;

import com.yangfang.aries.elasticsearch.SpringbootApplicationTests;
import com.yangfang.aries.elasticsearch.config.BaseConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.main.MainResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * info api test
 *
 * @author 幽明
 * @serial 2018/11/14
 */
@Slf4j
class ElasticSearchInfoAPITest extends SpringbootApplicationTests {

    @Test
    @DisplayName("info api")
    void testInfo() throws IOException {
        RestHighLevelClient client = BaseConfiguration.restHighLevelClient();
        Assert.assertNotNull(client);
        MainResponse response = client.info(RequestOptions.DEFAULT);
        log.info("{}", toString(response));

    }

    private String toString(MainResponse response) {
        StringBuilder sb = new StringBuilder();
        sb.append("MainResponse:[")
        .append("clusterName=").append(response.getClusterName())
        .append(",clusterUuid=").append(response.getClusterUuid())
        .append(",nodeName=").append(response.getNodeName())
        .append(",version=").append(response.getVersion())
        .append(",build=").append(response.getBuild())
        .append("]");
        return sb.toString();
    }
}
