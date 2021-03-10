/*
 * Cai.xin Inc.
 * Copyright (c) 2016-2019 All Rights Reserved.
 */

package com.yangfang.aries.jsr303;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * TODO 类描述
 *
 * @author 幽明
 * @serial 2019/1/24
 */
public class ConvertGroupTest {

    @Test
    public void test() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();


        // create a car and validate. The Driver is still null and does not get validated
        Car car = new Car("VW", "USD-123", 4);
        car.setPassedVehicleInspection(true);
        Set<ConstraintViolation<Car>> constraintViolations = validator.validate(car);
        assertEquals(0, constraintViolations.size());


        // create a driver who has not passed the driving test
        Driver john = new Driver();
        john.setAge(18);

        // now let's add a driver to the car
        car.setDriver(john);
        constraintViolations = validator.validate(car);
        assertEquals(1, constraintViolations.size());
        assertEquals(
                "The driver constraint should also be validated as part of the default group",
                constraintViolations.iterator().next().getMessage(),
                "You first have to pass the driving test"
        );
    }
}
