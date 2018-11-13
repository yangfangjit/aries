package com.yangfang.aries.junit5;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Junit 5 test case
 *
 * @author 幽明
 * @serial 2018/11/13
 */
@Slf4j
public class JUnit5Test extends SpringbootApplicationTests {

    @Test
    @DisplayName("好用")
    void myFirstTest() {
        log.info("test DisplayName annotation");
        assertEquals(2, 1 + 1);
    }
}
