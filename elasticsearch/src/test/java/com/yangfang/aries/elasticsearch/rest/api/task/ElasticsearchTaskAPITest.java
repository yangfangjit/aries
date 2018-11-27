package com.yangfang.aries.elasticsearch.rest.api.task;

import com.yangfang.aries.elasticsearch.SpringbootApplicationTests;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.TaskOperationFailure;
import org.elasticsearch.action.admin.cluster.node.tasks.list.ListTasksRequest;
import org.elasticsearch.action.admin.cluster.node.tasks.list.ListTasksResponse;
import org.elasticsearch.action.admin.cluster.node.tasks.list.TaskGroup;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.tasks.TaskId;
import org.elasticsearch.tasks.TaskInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * task api
 *
 * @author 幽明
 * @serial 2018/11/15
 */
class ElasticsearchTaskAPITest extends SpringbootApplicationTests {

    @Autowired
    private RestHighLevelClient client;

    @Test
    @DisplayName("List task api")
    void testListTaskAPI() throws IOException {
        ListTasksRequest request = new ListTasksRequest();
        request.setActions("cluster:*");
        request.setNodes("nodeId1", "nodeId2");
        request.setParentTaskId(new TaskId("parentTaskId", 42));

        request.setDetailed(true);

        request.setWaitForCompletion(true);
        request.setTimeout(TimeValue.timeValueSeconds(50));
        request.setTimeout("50s");

        ListTasksResponse response = client.tasks().list(request, RequestOptions.DEFAULT);
        Map<String, List<TaskInfo>> perNodeTasks = response.getPerNodeTasks();
        List<TaskGroup> groups = response.getTaskGroups();
        List<ElasticsearchException> nodeFailures = response.getNodeFailures();
        List<TaskOperationFailure> taskFailures = response.getTaskFailures();

    }
}
