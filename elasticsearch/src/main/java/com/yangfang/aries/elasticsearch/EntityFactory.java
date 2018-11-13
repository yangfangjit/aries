package com.yangfang.aries.elasticsearch;

import org.elasticsearch.common.Strings;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;
import java.util.Date;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * 实体创建工厂
 *
 * @author 幽明
 * @serial 2018/11/8
 */
public class EntityFactory {

    public static String getJsonString() throws IOException {
        XContentBuilder builder = jsonBuilder()
                .startObject()
                .field("user", "kimchy")
                .field("postDate", new Date())
                .field("message", "trying out Elasticsearch")
                .endObject();

        return Strings.toString(builder);
    }
}
