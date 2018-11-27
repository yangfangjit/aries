package com.yangfang.aries.elasticsearch.config;

import com.yangfang.aries.elasticsearch.enums.URI;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * bean configuration
 *
 * @author 幽明
 * @serial 2018/11/15
 */
@Configuration
public class BeanConfiguration {

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        return new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost(com.yangfang.aries.elasticsearch.context.Configuration.DEFAULT_ELASTICSEARCH_REST_HOSTNAME,
                                com.yangfang.aries.elasticsearch.context.Configuration.DEFAULT_ELASTICSEARCH_REST_PORT,
                                URI.HTTP.getCode())));
    }
}
