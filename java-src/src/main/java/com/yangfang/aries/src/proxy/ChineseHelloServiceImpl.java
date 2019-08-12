/*
 * Cai.xin Inc.
 * Copyright (c) 2016-2019 All Rights Reserved.
 */

package com.yangfang.aries.src.proxy;

/**
 * sayHello的中文实现
 *
 * @author 幽明
 * @serial 2019-08-11
 */
public class ChineseHelloServiceImpl implements HelloService {

    @Override
    public void sayHello() {
        System.out.println("世界你好");
    }
}
