/*
 * Cai.xin Inc.
 * Copyright (c) 2016-2019 All Rights Reserved.
 */

package com.yangfang.aries.src.proxy;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * HelloService的InvocationHandler实现
 *
 * @author 幽明
 * @serial 2019-08-11
 */
@Slf4j
public class HelloServiceInvocationHandler implements InvocationHandler {

    private Object helloService;

    public HelloServiceInvocationHandler(Object helloService) {
        this.helloService = helloService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("before say hello");
        method.invoke(helloService, args);
        log.info("after say hello");
        return null;
    }

    public static void main(String[] args) {
        InvocationHandler englishHandler = new HelloServiceInvocationHandler(new HelloServiceImpl());
        InvocationHandler chineseHandler = new HelloServiceInvocationHandler(new ChineseHelloServiceImpl());

        HelloService english = (HelloService) Proxy.newProxyInstance(
                HelloService.class.getClassLoader(),
                new Class[]{HelloService.class},
                englishHandler);

        english.sayHello();

        HelloService chinese = (HelloService) Proxy.newProxyInstance(
                HelloService.class.getClassLoader(),
                new Class[]{HelloService.class},
                chineseHandler);

        chinese.sayHello();
    }

}
