package io.spring.lab.store;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class StoreApplicationTests extends SpringTestBase {

	@Autowired
	ApplicationContext context;

	@Test
	public void contextLoads() {
		assertThat(context).isNotNull();
	}
}
