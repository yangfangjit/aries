package com.yangfang.aries.elasticsearch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootApplicationTests.class)
@ComponentScan(basePackages = {
		"com.yangfang.elasticsearch"
})
public class SpringbootApplicationTests {

	@Test
	public void contextLoads() {
	}

}
