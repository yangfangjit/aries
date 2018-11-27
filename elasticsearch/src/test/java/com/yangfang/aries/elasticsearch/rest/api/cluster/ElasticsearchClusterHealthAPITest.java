package com.yangfang.aries.elasticsearch.rest.api.cluster;

import com.yangfang.aries.elasticsearch.SpringbootApplicationTests;
import com.yangfang.aries.elasticsearch.context.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.support.ActiveShardCount;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.elasticsearch.cluster.health.ClusterIndexHealth;
import org.elasticsearch.common.Priority;
import org.elasticsearch.common.unit.TimeValue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Map;


/**
 * cluster health api
 *
 * @author 幽明
 * @serial 2018/11/15
 */
@Slf4j
class ElasticsearchClusterHealthAPITest extends SpringbootApplicationTests {

    @Autowired
    private RestHighLevelClient client;

    @Test
    @DisplayName("health api")
    void testHealthAPI() throws IOException {
        ClusterHealthRequest request = new ClusterHealthRequest(Configuration.DEFAULT_ELASTIC_INDEX);
        request.timeout(TimeValue.timeValueSeconds(50));
        request.masterNodeTimeout(TimeValue.timeValueSeconds(20));
        request.waitForYellowStatus();
        request.waitForEvents(Priority.NORMAL);
        request.level(ClusterHealthRequest.Level.SHARDS);
        request.waitForNoRelocatingShards(true);
        request.waitForNoInitializingShards(true);
        request.waitForNodes("2");
        // request.waitForNodes(">=2");
        // request.waitForNodes("le(2)");
        request.waitForActiveShards(ActiveShardCount.ALL);
        request.local(true);
        ClusterHealthResponse response = client.cluster().health(request, RequestOptions.DEFAULT);
        log.info("", response);
        // 集群
        String clusterName = response.getClusterName();
        ClusterHealthStatus status = response.getStatus();
        int numberOfNodes = response.getNumberOfNodes();
        Map<String, ClusterIndexHealth> indices = response.getIndices();

        //  索引
        ClusterIndexHealth index = indices.get(Configuration.DEFAULT_ELASTIC_INDEX);
        log.info("{}", index);
        ClusterHealthStatus indexStatus = index.getStatus();
        int numberOfShards = index.getNumberOfShards();
        int numberOfReplicas = index.getNumberOfReplicas();
        int activeShards = index.getActiveShards();
        int activePrimaryShards = index.getActivePrimaryShards();
        int initializingShards = index.getInitializingShards();
        int relocatingShards = index.getRelocatingShards();
        int unassignedShards = index.getUnassignedShards();
    }
}
