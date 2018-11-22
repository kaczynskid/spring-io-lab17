package io.spring.lab.warehouse;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.SocketUtils;

@SpringBootApplication
public class WarehouseApplication {

	public static final String INSTANCE_ID = "info.instanceId";

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(WarehouseApplication.class);
		app.setDefaultProperties(defaultProperties());
		app.run(args);
	}

	private static Map<String, Object> defaultProperties() {
		Map<String, Object> props = new HashMap<>();

		String instanceId = UUID.randomUUID().toString().replaceAll("-", "");
		props.put(INSTANCE_ID, instanceId);

		int serverPort = SocketUtils.findAvailableTcpPort();
		props.put("server.port", serverPort);

		int managementPort = SocketUtils.findAvailableTcpPort();
		props.put("management.server.port", managementPort);
		props.put("eureka.instance.metadata-map.management.port", managementPort);

		return props;
	}
}
