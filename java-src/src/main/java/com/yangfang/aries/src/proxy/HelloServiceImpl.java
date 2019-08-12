/*
 * Cai.xin Inc.
 * Copyright (c) 2016-2019 All Rights Reserved.
 */

package com.yangfang.aries.src.proxy;

/**
 * 默认的sayHello实现，英文的
 *
 * @author 幽明
 * @serial 2019-08-11
 */
public class HelloServiceImpl implements HelloService {

    @Override
    public void sayHello() {
        System.out.println("Hello world");
    }
}
