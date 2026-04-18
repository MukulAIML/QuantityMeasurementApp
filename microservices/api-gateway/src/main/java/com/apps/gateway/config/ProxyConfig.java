package com.apps.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ProxyConfig {

    @Value("${auth.service.url}")
    private String authServiceUrl;

    @Value("${measurement.service.url}")
    private String measurementServiceUrl;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean(name = "authServiceUrl")
    public String authServiceUrl() {
        return authServiceUrl;
    }

    @Bean(name = "measurementServiceUrl")
    public String measurementServiceUrl() {
        return measurementServiceUrl;
    }
}
