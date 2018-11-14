package com.yangfang.aries.elasticsearch.rest.api.cluster;

import com.yangfang.aries.elasticsearch.SpringbootApplicationTests;
import com.yangfang.aries.elasticsearch.config.BaseConfiguration;
import lombok.extern.slf4j.Slf4j;
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

import java.io.IOException;

/**
 * cluster setting api
 *
 * @author 幽明
 * @serial 2018/11/14
 */
@Slf4j
class ElasticsearchUpdateClusterSettingAPITest extends SpringbootApplicationTests {

    @Test
    @DisplayName("update cluster setting api")
    void testUpdateClusterSetting() throws IOException {
        RestHighLevelClient client = BaseConfiguration.restHighLevelClient();
        Assert.assertNotNull(client);

        ClusterUpdateSettingsRequest request = new ClusterUpdateSettingsRequest();
        String transientSettingKey =
                RecoverySettings.INDICES_RECOVERY_MAX_BYTES_PER_SEC_SETTING.getKey();
        int transientSettingValue = 10;
        Settings transientSettings =
                Settings.builder()
                        .put(transientSettingKey, transientSettingValue, ByteSizeUnit.BYTES)
                        .build();

        String persistentSettingKey =
                EnableAllocationDecider.CLUSTER_ROUTING_ALLOCATION_ENABLE_SETTING.getKey();
        String persistentSettingValue =
                EnableAllocationDecider.Allocation.NONE.name();
        Settings persistentSettings =
                Settings.builder()
                        .put(persistentSettingKey, persistentSettingValue)
                        .build();
        request.transientSettings(transientSettings);
        request.persistentSettings(persistentSettings);

        ClusterUpdateSettingsResponse response = client.cluster().putSettings(request, RequestOptions.DEFAULT);
        log.info("{}", response);
        Assert.assertNotNull(response);
        Assert.assertTrue(response.isAcknowledged());
        Assert.assertNotNull(response.getTransientSettings());
        Assert.assertNotNull(response.getPersistentSettings());
    }
}
