package io.spring.lab.demo;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT, properties = {
        "greet.template=Hello %s"
})
public class GreetingControllerTest extends SpringTestBase {

    @Autowired
    TestRestTemplate rest;

    @Test
    public void shouldGreetMe() {
        Greeting greeting = rest.getForObject("/greet/me", Greeting.class);

        assertThat(greeting).isNotNull();
        assertThat(greeting.getMessage()).isEqualTo("Hello me");
    }
}