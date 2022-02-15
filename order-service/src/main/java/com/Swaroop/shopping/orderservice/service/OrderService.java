package com.Swaroop.shopping.orderservice.service;

import lombok.AllArgsConstructor;
import lombok.Synchronized;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.Swaroop.shopping.orderservice.datab.OrderRepository;
import com.Swaroop.shopping.orderservice.entity.Order;
import com.Swaroop.shopping.orderservice.entity.ShippingAddress;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order placeOrder(Order order) {
        order.setOrderDate(Instant.now());
        return orderRepository.save(order);
    }

    public void changeOrderStatus(int orderId, int status) {
        Optional<Order> byOrderId = orderRepository.findByOrderId(orderId);

        if (byOrderId.isPresent()) {
            Order order = byOrderId.get();
            order.setOrderStatus(status);
            orderRepository.save(order);
        }

    }
    public void deleteOrder(int orderId) {
        Optional<Order> byOrderId = orderRepository.findByOrderId(orderId);

        byOrderId.ifPresent(orderRepository::delete);
    }
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    public Optional<Order> getOrderById(int orderId) {
        return orderRepository.findByOrderId(orderId);
    }
    public List<Order> getOrderByCustomerId(String customerId) {
        return orderRepository.findByCustomerId(customerId);
    }

    public Order getLatestOrder() {
        return orderRepository.findTopByOrderByOrderDateDesc();
    }

    @Synchronized
    public int getNextId() {
        Order order = orderRepository.findTopByOrderByOrderIdDesc();
        int id = (order != null) ? order.getOrderId() : 0;
        return ++id;
    }

    public Optional<Order> findById(String id) {
        return orderRepository.findById(id);
    }

    public Page<Order> findByBuyerEmail(String name, PageRequest request) {
        return orderRepository.findByBuyerEmail(name, request);
    }

    public Page<Order> findAll(PageRequest request) {
        return orderRepository.findAll(request);
    }
}
