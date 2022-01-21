package com.Swaroop.shopping.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "PROFILE-SERVICE", url = "http://localhost:8080/api/profile", configuration = MyRestTemplateConfig.class)
public interface ProfileServiceClient {
    @GetMapping(value = "/email/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<UserProfile> getByEmail(@PathVariable(value = "email") String email);
}
