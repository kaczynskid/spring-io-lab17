package io.spring.lab.dashboard;

import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableHystrixDashboard
@EnableTurbine
public class DashboardCloudConfig {

}
