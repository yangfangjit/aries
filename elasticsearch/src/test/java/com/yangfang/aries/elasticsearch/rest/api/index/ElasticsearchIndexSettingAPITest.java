package com.yangfang.aries.elasticsearch.rest.api.index;

import com.yangfang.aries.elasticsearch.SpringbootApplicationTests;
import com.yangfang.aries.elasticsearch.config.BaseConfiguration;
import com.yangfang.aries.elasticsearch.context.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsRequest;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsResponse;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsRequest;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 * index setting api
 *
 * @author 幽明
 * @serial 2018/11/14
 */
@Slf4j
class ElasticsearchIndexSettingAPITest extends SpringbootApplicationTests {

    @Test
    @DisplayName("update indices setting api")
    void testUpdateIndexSetting() throws IOException {
        RestHighLevelClient client = BaseConfiguration.restHighLevelClient();
        Assert.assertNotNull(client);

        UpdateSettingsRequest request = new UpdateSettingsRequest(Configuration.DEFAULT_ELASTIC_INDEX);

        String settingKey = "index.number_of_replicas";
        int settingValue = 0;
        Settings.Builder settingsBuilder = Settings.builder()
                        .put(settingKey, settingValue);
        request.settings(settingsBuilder);

        UpdateSettingsResponse response = client.indices().putSettings(request, RequestOptions.DEFAULT);
        log.info("{}", response);
        Assert.assertNotNull(response);
        Assert.assertTrue(response.isAcknowledged());
    }

    @Test
    @DisplayName("get indices setting api")
    void testGetIndexSetting() throws IOException {
        RestHighLevelClient client = BaseConfiguration.restHighLevelClient();
        Assert.assertNotNull(client);

        GetSettingsRequest request = new GetSettingsRequest().indices(Configuration.DEFAULT_ELASTIC_INDEX);
        String settingKey = "index.number_of_replicas";
        request.names(settingKey);

        GetSettingsResponse response = client.indices().getSettings(request, RequestOptions.DEFAULT);
        log.info("{}", response);
        Assert.assertNotNull(response);
        Assert.assertNotNull(response.getSetting(Configuration.DEFAULT_ELASTIC_INDEX, settingKey));
    }
}
