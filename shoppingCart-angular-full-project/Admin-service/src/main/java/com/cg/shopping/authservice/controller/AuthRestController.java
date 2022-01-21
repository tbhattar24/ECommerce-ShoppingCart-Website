package com.cg.shopping.authservice.controller;

import com.cg.shopping.authservice.client.ProfileServiceClient;
import com.cg.shopping.authservice.client.UserProfile;
import com.cg.shopping.authservice.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@AllArgsConstructor
@Slf4j
public class AuthRestController {

    private final JwtUtil jwtUtil;
    private final ProfileServiceClient profileServiceClient;

    @PostMapping(value = "/auth/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login(@RequestBody Map<String, String> user) {
        ResponseEntity<UserProfile> login = profileServiceClient.login(user);
        UserProfile profile = login.getBody();
        if (!StringUtils.isEmpty(profile.getEmail()) && login.getStatusCode() == HttpStatus.OK) {
            String token = jwtUtil.generateToken(login.getBody().getEmail(), profile.getRole());
            return new ResponseEntity<>(token, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid Credential Provided", HttpStatus.OK);
        }
    }

    @PostMapping(value = "/auth/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> register(@RequestBody UserProfile user) {
        // Persist user to some persistent storage
        ResponseEntity<UserProfile> userProfileResponseEntity = profileServiceClient.addNewCustomer(user);
        System.out.println("Info saved...");

        if (userProfileResponseEntity.getBody() != null) {
            return new ResponseEntity<String>("User Registered Successfully", HttpStatus.OK);
        } else  {
            return new ResponseEntity<>("Error occurred while registering user", HttpStatus.OK);
        }
    }

}