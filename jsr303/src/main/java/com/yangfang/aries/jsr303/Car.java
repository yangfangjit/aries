/*
 * Cai.xin Inc.
 * Copyright (c) 2016-2019 All Rights Reserved.
 */

package com.yangfang.aries.jsr303;

import com.sun.istack.internal.NotNull;
import com.yangfang.aries.jsr303.check.CarChecks;
import com.yangfang.aries.jsr303.check.DriverChecks;

import javax.validation.Valid;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

/**
 * TODO 类描述
 *
 * @author 幽明
 * @serial 2019/1/24
 */
public class Car {

    @NotNull
    private String manufacturer;

    @NotNull
    @Size(min = 2, max = 14)
    private String licensePlate;

    @Min(2)
    private int seatCount;

    @AssertTrue(
            message = "The car has to pass the vehicle inspection first",
            groups = CarChecks.class
    )
    private boolean passedVehicleInspection;

    @Valid
    @ConvertGroup(from = Default.class, to = DriverChecks.class)
    private Driver driver;

    public Car(String manufacturer, String licencePlate, int seatCount) {
        this.manufacturer = manufacturer;
        this.licensePlate = licencePlate;
        this.seatCount = seatCount;
    }

    public boolean isPassedVehicleInspection() {
        return passedVehicleInspection;
    }

    public void setPassedVehicleInspection(boolean passedVehicleInspection) {
        this.passedVehicleInspection = passedVehicleInspection;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}
