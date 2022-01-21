package com.Swaroop.shopping.orderservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "WALLET-SERVICE", url = "http://localhost:8080/api/wallet", configuration = MyRestTemplateConfig.class)
public interface WalletServiceClient {
    @PostMapping(value = "/payMoney", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Void> payMoney(@RequestBody WalletRequest wallet);
    @GetMapping(value = "/customer/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Wallet> getByCustomerId(@PathVariable String customerId);
}
