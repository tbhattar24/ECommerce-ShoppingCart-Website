package com.Swaroop.shopping.orderservice.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Swaroop.shopping.orderservice.client.Cart;
import com.Swaroop.shopping.orderservice.client.CartServiceClient;
import com.Swaroop.shopping.orderservice.client.ProfileServiceClient;
import com.Swaroop.shopping.orderservice.client.UserProfile;
import com.Swaroop.shopping.orderservice.client.Wallet;
import com.Swaroop.shopping.orderservice.client.WalletRequest;
import com.Swaroop.shopping.orderservice.client.WalletServiceClient;
import com.Swaroop.shopping.orderservice.entity.Order;
import com.Swaroop.shopping.orderservice.entity.ShippingAddress;
import com.Swaroop.shopping.orderservice.service.OrderService;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@AllArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;
    private final CartServiceClient cartServiceClient;
    private final WalletServiceClient walletServiceClient;
    private final ProfileServiceClient profileServiceClient;

    @PostMapping(value = "/placeOrder", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> placeOrder(@RequestBody Order order) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.placeOrder(order));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> makeOrder(Authentication auth) {
        Cart cart = cartServiceClient.getCart(auth.getCredentials().toString());
        UserProfile byEmail = profileServiceClient.getByEmail(auth.getName()).getBody();

        //create Order

        BigDecimal priceTotal = cart.getProducts()
                .stream()
                .map(item -> item.getPrice().multiply(new BigDecimal(item.getCount())))
                .reduce(BigDecimal::add)
                .orElse(new BigDecimal(0));
        Order order = Order
                .builder()
                .productInOrders(cart.getProducts())
                .customerId(auth.getName())
                .orderStatus(0)
                .orderAmount(priceTotal)
                .buyerAddress(byEmail.getAddress())
                .buyerEmail(auth.getName())
                .buyerName(byEmail.getFullName())
                .buyerPhone(String.valueOf(byEmail.getMobileNumber()))
                .build();
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.placeOrder(order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> addOrderItems(@PathVariable String id){
        Order order = orderService.findById(id).get();
        return ResponseEntity.status(HttpStatus.OK).body(order);
    }


    @PostMapping(value = "/onlinePayment", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> onlinePayment(@RequestBody Order order) {

        ResponseEntity<UserProfile> byEmail = profileServiceClient.getByEmail(order.getBuyerEmail());
        String customerId = byEmail.getBody().get_id();

        log.info("Received payment request for order " + order.get_id() +  " For Customer " + customerId);

        ResponseEntity<Wallet> byCustomerId = walletServiceClient.getByCustomerId(customerId);
        Wallet body = byCustomerId.getBody();
        if (body.getWalletId() != 0) {
            WalletRequest walletRequest = WalletRequest
                    .builder()
                    .walletId(body.getWalletId())
                    .amount(order.getOrderAmount().doubleValue())
                    .transactionType("Withdraw")
                    .build();
            ResponseEntity<Void> payMoney = walletServiceClient.payMoney(walletRequest);
            if (payMoney.getStatusCode() == HttpStatus.OK) {
                changeOrderStatus(1, order.getOrderId());
            } else {
                changeOrderStatus(2, order.getOrderId());
            }
        } else {
            changeOrderStatus(3, order.getOrderId());
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(orderService.getOrderById(order.getOrderId()).get());
    }

    @GetMapping(value = "/changeStatus/{status}/{orderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> changeOrderStatus(@RequestParam (value = "status") int status,
                                                  @RequestParam (value = "orderId") int orderId) {
        orderService.changeOrderStatus(orderId, status);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @GetMapping(value = "/allOrder", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Order>> getAllOrder() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping(value = "/findMaxByOrder", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> getLatestOrder() {
        return ResponseEntity.ok(orderService.getLatestOrder());
    }


    @GetMapping(value = "/customer/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Order>> getByCustomerId(@PathVariable("customerId") String customerId ) {
        return ResponseEntity.ok(orderService.getOrderByCustomerId(customerId));
    }

    @DeleteMapping(value = "delete/{orderId}",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateOrder(@RequestParam(value = "orderId") int orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @GetMapping("")
    public Page<Order> orderList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", defaultValue = "10") Integer size,
                                     Authentication authentication) {
        PageRequest request = PageRequest.of(page - 1, size);
        Page<Order> orderPage;
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_CUSTOMER"))) {
            orderPage = orderService.findByBuyerEmail(authentication.getName(), request);
        } else {
            orderPage = orderService.findAll(request);
        }
        return orderPage;
    }
}
