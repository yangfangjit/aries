package com.yangfang.aries.elasticsearch.rest.api.miscellaneous;

import com.yangfang.aries.elasticsearch.SpringbootApplicationTests;
import com.yangfang.aries.elasticsearch.config.BaseConfiguration;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.protocol.xpack.XPackInfoRequest;
import org.elasticsearch.protocol.xpack.XPackInfoResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.EnumSet;

/**
 * x-pack api test
 *
 * @author 幽明
 * @serial 2018/11/14
 */
class ElasticsearchXpackAPITest extends SpringbootApplicationTests {

    @Test
    @DisplayName("X-Pack api")
    void testXPack() throws IOException {
        RestHighLevelClient client = BaseConfiguration.restHighLevelClient();
        XPackInfoRequest request = new XPackInfoRequest();
        request.setVerbose(true);
        request.setCategories(EnumSet.of(
                XPackInfoRequest.Category.BUILD,
                XPackInfoRequest.Category.LICENSE,
                XPackInfoRequest.Category.FEATURES));
        XPackInfoResponse response = client.xpack().info(request, RequestOptions.DEFAULT);

        XPackInfoResponse.BuildInfo build = response.getBuildInfo();
        XPackInfoResponse.LicenseInfo license = response.getLicenseInfo();

        XPackInfoResponse.FeatureSetsInfo features = response.getFeatureSetsInfo();
    }
}
