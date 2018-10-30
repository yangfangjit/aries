/*
 * Cai.xin Inc.
 * Copyright (c) 2016-2018 All Rights Reserved.
 */

package com.yangfang.aries.jvm.variadic;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试可变参数
 *
 * @author 幽明
 * @serial 2018/7/28
 */
public class VariadicParameters {

    public static void variadicParametersMethod(List<String> params) {
        System.out.println("normalMethod is being called");
    }

    public static void variadicParametersMethod(String param1, String param2, String... params) {
        System.out.println("variadicParametersMethod is being called");
    }

    public static void variadicParametersMethod(String... params) {
        System.out.println("variadicParametersMethod2 is being called");
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        VariadicParameters.variadicParametersMethod(list);

        list.add("yang");
        list.add("yang");
        variadicParametersMethod(list);

    }
}
