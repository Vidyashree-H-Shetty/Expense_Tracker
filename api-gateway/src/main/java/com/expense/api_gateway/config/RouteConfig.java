//package com.expense.api_gateway.config;
//
//import org.springframework.cloud.gateway.route.*;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.*;
//
//
//import org.springframework.cloud.gateway.route.*;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RouteConfig {
//
//    @Bean
//    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("auth-service", r -> r
//                        .path("/api/auth/**")
//                        .uri("lb://auth-service"))
//                .route("expense-service", r -> r
//                        .path("/expenses/**")
//                        .filters(f -> f.rewritePath("/expenses/(?<segment>.*)", "/$\\{segment}"))
//                        .uri("lb://expense-service"))
//                .route("report-service", r -> r
//                        .path("/reports/**")
//                        .filters(f -> f.rewritePath("/reports/(?<segment>.*)", "/$\\{segment}"))
//                        .uri("lb://report-service"))
//                .route("expense-service", r -> r
//                        .path("/categories/**")
//                        .filters(f -> f.rewritePath("/expenses/(?<segment>.*)", "/$\\{segment}"))
//                        .uri("lb://expense-service"))
//                .route("expense-service", r -> r
//                        .path("/budget/**")
//                        .filters(f -> f.rewritePath("/expenses/(?<segment>.*)", "/$\\{segment}"))
//                        .uri("lb://expense-service"))
//                .build();
//    }
//}
//
//
