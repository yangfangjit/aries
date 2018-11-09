/*
 * Cai.xin Inc.
 * Copyright (c) 2016-2018 All Rights Reserved.
 */

package com.yangfang.elasticsearch;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 测试类上下文
 *
 * @author 幽明
 * @serial 2018/11/8
 */
public class Configuration {

    public static final String DEFAULT_ELASTICSEARCH_REST_HOSTNAME = "localhost";
    public static final Integer DEFAULT_ELASTICSEARCH_REST_PORT = 9200;

    public static final Integer DEFAULT_ELASTICSEARCH_PORT = 9300;

    public static final String DEFAULT_ELASTIC_INDEX = "twitter";
    public static final String DEFAULT_ELASTIC_TYPE = "_doc";
    public static final String DEFAULT_ELASTICSEARCH_DOC_ID = "10";

    public static InetAddress defaultHost() throws UnknownHostException {
        return InetAddress.getLocalHost();
    }
}
