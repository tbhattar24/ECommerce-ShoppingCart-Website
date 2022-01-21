package com.Swaroop.shopping.gatewayservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.Swaroop.shopping.gatewayservice.filter.JwtAuthenticationFilter;


@Configuration
public class SpringCloudConfig {

   

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
			
        		
                .route("product-service", r -> r.path("/api/products/**")
                		.uri("lb://product-service"))
                
                .route("profile-service", r -> r.path("/api/profile/**")
                        .uri("lb://profile-service"))
                
                .route("cart-service", r -> r.path("/api/cart/**")
                        .uri("lb://cart-service"))
                
                .route("order-service", r -> r.path("/api/orders/**")
                        .uri("lb://order-service"))
                
                .route("wallet-service", r -> r.path("/api/wallet/**")
                        .uri("lb://wallet-service"))
                
                .build();
    }
}
