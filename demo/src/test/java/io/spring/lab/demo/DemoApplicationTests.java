package io.spring.lab.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class DemoApplicationTests {

	@Autowired
	ApplicationContext context;

	@Autowired
	TestRestTemplate rest;

	@Test
	public void contextLoads() {
		assertThat(context).isNotNull();
	}

	@Test
	public void shouldGreetMe() {
		Greeting greeting = rest.getForObject("/greet/me", Greeting.class);

		assertThat(greeting).isNotNull();
		assertThat(greeting.getMessage()).isEqualTo("Hi me");
	}
}
