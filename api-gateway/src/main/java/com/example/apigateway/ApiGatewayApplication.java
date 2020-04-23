package com.example.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.security.oauth2.gateway.TokenRelayGatewayFilterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@EnableEurekaClient
@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder,
                                           TokenRelayGatewayFilterFactory filterFactory) {
        return builder.routes()
                .route("car-service", r -> r.path("/cars")
                        .filters(f -> f.filter(filterFactory.apply()))
                        // .filters(f -> f.hystrix(c -> c.setName("carsFallback")
                        // .setFallbackUri("forward:/cars-fallback")))
                        .uri("lb://car-service")) //downstream endpoint  lb - load balanced
                .route("second-service", r -> r.path("/second-service/**")
                        .filters(f -> {
                            f.rewritePath("/second-service/(?<segment>.*)", "/${segment}");
                            f.filter(filterFactory.apply());
                            f.hystrix(c -> c.setName("carsFallback")
                                    .setFallbackUri("forward:/second"));
                            return f;
                        })
                        .uri("lb://second-service"))
                .route("group-service", r -> r.path("/group-service/**")
                        .filters(f -> {
                            f.rewritePath("/group-service/(?<segment>.*)", "/${segment}");
                            f.filter(filterFactory.apply());
                            f.hystrix(c -> c.setName("groupsFallback")
                                    .setFallbackUri("forward:/group"));
                            return f;
                        })
                        .uri("lb://group-service")) //downstream endpoint  lb - load balanced
                .build();
    }

    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }
}

