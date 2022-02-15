package com.Swaroop.shopping.cartservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Swaroop.shopping.cartservice.dto.ProductInOrder;
import com.Swaroop.shopping.cartservice.entity.Cart;
import com.Swaroop.shopping.cartservice.service.CartService;

import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/cart")
@AllArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_VALUE)
    public boolean addCart(@RequestBody ProductInOrder cart, Authentication auth) {
        try {
            mergeCart(Collections.singleton(cart), auth);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @PostMapping("")
    public ResponseEntity<Cart> mergeCart(@RequestBody Set<ProductInOrder> productInOrders, Authentication auth) {
        try {
            cartService.mergeLocalCart(productInOrders, auth.getName());
        } catch (Exception e) {
            ResponseEntity.badRequest().body("Merge Cart Failed");
        }
        return ResponseEntity.ok(cartService.getCart(auth.getName()));
    }

    @GetMapping("")
    public Cart getCart(Authentication auth) {
        return cartService.getCart(auth.getName());
    }

    @GetMapping(value = "/allCart", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Cart>> getAllCart() {
        return ResponseEntity.ok(cartService.getAllCart());
    }

    @PostMapping("/checkout")
    public ResponseEntity<Map<String, String>> checkout(Authentication auth) {
        String orderId = cartService.checkout(auth);
        Map<String, String> order = new HashMap<>();
        order.put("orderId", orderId);
        return ResponseEntity.ok(order);
    }

}
