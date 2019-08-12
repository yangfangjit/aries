/*
 * Cai.xin Inc.
 * Copyright (c) 2016-2019 All Rights Reserved.
 */

package com.yangfang.aries.src.proxy;

import lombok.extern.slf4j.Slf4j;

/**
 * 静态代理
 *
 * @author 幽明
 * @serial 2019-08-11
 */
@Slf4j
public class HelloServiceProxy implements HelloService {

    private HelloService realObject;

    public HelloServiceProxy(HelloService realObject) {
        this.realObject = realObject;
    }

    @Override
    public void sayHello() {
        log.info("before say hello");
        realObject.sayHello();
        log.info("after say hello");
    }

    public static void main(String[] args) {
        HelloService realObject = new HelloServiceImpl();
        HelloService proxy = new HelloServiceProxy(realObject);
        proxy.sayHello();
    }
}
