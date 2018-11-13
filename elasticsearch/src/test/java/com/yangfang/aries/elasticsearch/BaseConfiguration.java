/*
 * Cai.xin Inc.
 * Copyright (c) 2016-2018 All Rights Reserved.
 */

package com.yangfang.aries.elasticsearch;

import com.yangfang.aries.elasticsearch.enums.URI;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * base configuration
 *
 * @author 幽明
 * @serial 2018/11/13
 */
@Slf4j
@Configuration
@EnableAutoConfiguration
public class BaseConfiguration {

    @Bean
    public static RestHighLevelClient restHighLevelClient() {
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(com.yangfang.aries.elasticsearch.context.Configuration.DEFAULT_ELASTICSEARCH_REST_HOSTNAME,
                                com.yangfang.aries.elasticsearch.context.Configuration.DEFAULT_ELASTICSEARCH_REST_PORT,
                                URI.HTTP.getCode())));
    }
}
