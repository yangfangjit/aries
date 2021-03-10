/*
 * Cai.xin Inc.
 * Copyright (c) 2016-2019 All Rights Reserved.
 */

package com.yangfang.aries.jsr303;

import com.yangfang.aries.jsr303.check.DriverChecks;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * TODO 类描述
 *
 * @author 幽明
 * @serial 2019/1/24
 */
public class Driver {

    @NotNull
    private String name;

    @Min(
            value = 18,
            message = "You have to be 18 to drive a car",
            groups = DriverChecks.class
    )
    public int age;

    @AssertTrue(
            message = "You first have to pass the driving test",
            groups = DriverChecks.class
    )
    public boolean hasDrivingLicense;

    public Driver(String name) {
        this.name = name;
    }

    public Driver() {
    }

    public void passedDrivingTest(boolean b) {
        hasDrivingLicense = b;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
