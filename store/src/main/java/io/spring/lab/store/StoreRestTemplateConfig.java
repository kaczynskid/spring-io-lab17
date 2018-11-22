package io.spring.lab.store;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import io.spring.lab.cloud.ConditionalOnEurekaClient;
import io.spring.lab.cloud.ConditionalOnMissingEurekaClient;
import io.spring.lab.cloud.ConditionalOnMissingFeignClient;

@Configuration
@ConditionalOnMissingFeignClient
public class StoreRestTemplateConfig {

    @Configuration
    @ConditionalOnMissingEurekaClient
    static class UriRewritingRestTemplateConfig {

        @Bean
        RestTemplate restTemplate(RestTemplateBuilder builder) {
            return builder.build();
        }
    }

    @Configuration
    @ConditionalOnEurekaClient
    static class LoadBalancedRestTemplateConfig {

        @Bean
        @LoadBalanced
        RestTemplate restTemplate(RestTemplateBuilder builder) {
            return builder.build();
        }
    }
}
