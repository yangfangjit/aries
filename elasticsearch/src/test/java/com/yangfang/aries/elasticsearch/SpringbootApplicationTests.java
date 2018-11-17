package com.yangfang.aries.elasticsearch;

import com.yangfang.aries.elasticsearch.config.BeanConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringbootApplicationTests.class)
@Import(BeanConfiguration.class)
public class SpringbootApplicationTests {

	@Test
	public void contextLoads() {
	}

}
