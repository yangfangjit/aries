package com.yangfang.aries.elasticsearch.rest.api.cluster;

import com.yangfang.aries.elasticsearch.SpringbootApplicationTests;
import com.yangfang.aries.elasticsearch.config.BaseConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.cluster.settings.ClusterGetSettingsRequest;
import org.elasticsearch.action.admin.cluster.settings.ClusterGetSettingsResponse;
import org.elasticsearch.action.admin.cluster.settings.ClusterUpdateSettingsRequest;
import org.elasticsearch.action.admin.cluster.settings.ClusterUpdateSettingsResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.routing.allocation.decider.EnableAllocationDecider;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.indices.recovery.RecoverySettings;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * cluster setting api
 *
 * @author 幽明
 * @serial 2018/11/14
 */
@Slf4j
class ElasticsearchClusterSettingAPITest extends SpringbootApplicationTests {

    @Autowired
    private RestHighLevelClient client;

    @Test
    @DisplayName("update cluster setting api")
    void testUpdateClusterSetting() throws IOException {
        String transientSettingKey =
                RecoverySettings.INDICES_RECOVERY_MAX_BYTES_PER_SEC_SETTING.getKey();
        int transientSettingValue = 10;
        Settings transientSettings = Settings.builder()
                        .put(transientSettingKey, transientSettingValue, ByteSizeUnit.BYTES).build();

        String persistentSettingKey = EnableAllocationDecider.CLUSTER_ROUTING_ALLOCATION_ENABLE_SETTING.getKey();
        String persistentSettingValue = EnableAllocationDecider.Allocation.NONE.name();
        Settings persistentSettings = Settings.builder()
                        .put(persistentSettingKey, persistentSettingValue).build();

        ClusterUpdateSettingsRequest request = new ClusterUpdateSettingsRequest();
        request.transientSettings(transientSettings);
        request.persistentSettings(persistentSettings);

        ClusterUpdateSettingsResponse response = client.cluster().putSettings(request, RequestOptions.DEFAULT);
        log.info("{}", response);
        Assert.assertNotNull(response);
        Assert.assertTrue(response.isAcknowledged());
        Assert.assertNotNull(response.getTransientSettings());
        Assert.assertNotNull(response.getPersistentSettings());
    }

    @Test
    @DisplayName("get cluster setting api")
    void testGetClusterSetting() throws IOException {
        ClusterGetSettingsRequest request = new ClusterGetSettingsRequest();
        ClusterGetSettingsResponse response = client.cluster().getSettings(request, RequestOptions.DEFAULT);
        Settings persistentSettings = response.getPersistentSettings();
        Assert.assertNotNull(persistentSettings);
        Settings transientSettings = response.getTransientSettings();
        Assert.assertNotNull(transientSettings);
        Settings defaultSettings = response.getDefaultSettings();
        Assert.assertNotNull(defaultSettings);
        String settingValue = response.getSetting("cluster.routing.allocation.enable");
        Assert.assertNotNull(settingValue);
    }

}
