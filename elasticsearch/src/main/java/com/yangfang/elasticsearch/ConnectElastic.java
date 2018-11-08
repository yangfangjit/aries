/*
 * Cai.xin Inc.
 * Copyright (c) 2016-2018 All Rights Reserved.
 */
package com.yangfang.elasticsearch;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.xcontent.*;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.util.Date;

import static java.net.InetAddress.getLocalHost;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * elasticsearch 测试
 *
 * @author 幽明
 * @serial 2018/11/8
 */
@Slf4j
public class ConnectElastic {

    public static void main(String[] args) {
        try (TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(getLocalHost(), 9300))) {
            log.info("elasticsearch node num: {}", client.listedNodes().size());

            XContentBuilder builder = jsonBuilder()
                    .startObject()
                    .field("user", "kimchy")
                    .field("postDate", new Date())
                    .field("message", "trying out Elasticsearch")
                    .endObject();

            String json = Strings.toString(builder);
            IndexResponse response = client.prepareIndex("twitter", "_doc", "10")
                    .setSource(json, XContentType.JSON).get();

            // Index name
            String index = response.getIndex();
            // Type name
            String type = response.getType();
            // Document ID (generated or not)
            String id = response.getId();
            // Version (if it's the first time you index this document, you will get: 1)
            long version = response.getVersion();
            // status has stored current instance statement.
            RestStatus status = response.status();
            log.info("index-{}, type-{}, id-{}, version-{}, status-{}", index, type, id, version, status);
        } catch (IOException e) {
            log.warn("w", e);
        }
    }
}
