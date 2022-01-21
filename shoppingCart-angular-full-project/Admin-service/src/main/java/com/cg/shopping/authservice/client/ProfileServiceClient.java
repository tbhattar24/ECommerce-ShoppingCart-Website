package com.cg.shopping.authservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "PROFILE-SERVICE", url = "http://localhost:8080/profile", configuration = MyRestTemplateConfig.class)
public interface ProfileServiceClient {
    @PostMapping(value = "/createNewCustomer", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserProfile> addNewCustomer(@RequestBody UserProfile userProfile);

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserProfile> login(@RequestBody Map<String, String> userProfile);

}
