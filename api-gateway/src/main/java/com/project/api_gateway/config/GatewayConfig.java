package com.project.api_gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-route", r -> r
                        .path("/auth/**")
                        .uri("lb://auth-service"))
                .route("product-route", r -> r
                        .path("/product", "/product/**")
                        .uri("lb://product-service"))
                .route("scheduler-route", r -> r
                        .path("/scheduler/**")
                        .uri("lb://scheduler-service"))
                .build();
    }
}