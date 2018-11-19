package io.spring.lab.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.Banner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.util.Optional.ofNullable;

@Slf4j
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
//		SpringApplication.run(DemoApplication.class, args);
		SpringApplication app = new SpringApplication(DemoApplication.class);
		app.setBannerMode(Banner.Mode.CONSOLE);
		app.setLogStartupInfo(true);
		app.run(args);
	}

	@Bean
	ApplicationRunner init(Environment env) {
		return args ->
			log.info("### Started with greeting template: {}",
				ofNullable(env.getProperty("greet.template"))
					.orElseThrow(MissingGreetTemplate::new));
	}

}

class MissingGreetTemplate extends RuntimeException implements ExitCodeGenerator {

	@Override
	public int getExitCode() {
		return 11;
	}
}

@RestController
@AllArgsConstructor
class GreetingController {

	GreetingService service;

	@GetMapping("/greet/{name}")
	Greeting sayHello(@PathVariable String name) {
		return service.greet(name);
	}
}

@Component
class GreetingService {

	@Value("${greet.template:Hi %s}")
	String template;

	Greeting greet(String name) {
		return new Greeting(String.format(template, name));
	}

}

@lombok.Value
class Greeting {

	private String message;

	@JsonCreator
	public static Greeting of(@JsonProperty("message") String message) {
		return new Greeting(message);
	}
}
