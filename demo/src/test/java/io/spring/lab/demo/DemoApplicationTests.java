package io.spring.lab.demo;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
		"greet.template=whatever"
})
public class DemoApplicationTests extends SpringTestBase {

	@Autowired
	ApplicationContext context;

	@Test
	public void contextLoads() {
		assertThat(context).isNotNull();
	}
}
