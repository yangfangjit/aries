/*
 * Cai.xin Inc.
 * Copyright (c) 2016-2019 All Rights Reserved.
 */

package com.yangfang.aries.src.proxy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * HelloService的MethodInterceptor实现
 *
 * @author 幽明
 * @serial 2019-08-11
 */
@Slf4j
public class HelloServiceMethodInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        log.info("before say hello via CGLIB");
        methodProxy.invokeSuper(o, objects);
        log.info("after say hello Via CGLIB");
        return null;
    }

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(HelloServiceImpl.class);
        enhancer.setCallback(new HelloServiceMethodInterceptor());
        HelloService english = (HelloService) enhancer.create();
        log.info("proxy is {}", english.getClass().getName());
        english.sayHello();

        enhancer.setSuperclass(ChineseHelloServiceImpl.class);
        HelloService chinese = (HelloService) enhancer.create();
        log.info("proxy is {}", chinese.getClass().getName());
        chinese.sayHello();
    }
}
