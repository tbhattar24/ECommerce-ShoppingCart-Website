package com.Swaroop.shopping.cartservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "ORDER-SERVICE", url = "http://localhost:8080/api/orders", configuration = MyRestTemplateConfig.class)
public interface OrderServiceClient {
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Order> makeOrder(@RequestHeader("Authorization") String token) ;
}
