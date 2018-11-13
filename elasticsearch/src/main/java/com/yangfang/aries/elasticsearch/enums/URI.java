package com.yangfang.elasticsearch.enums;

import lombok.Getter;

/**
 * URI
 *
 * @author 幽明
 * @serial 2018/11/9
 */
public enum URI {
    /**
     * URI schema enum
     */
    HTTPS("https"),
    HTTP("http"),
    FTP("ftp");

    @Getter
    private String code;

    URI(String code) {
        this.code = code;
    }
}
