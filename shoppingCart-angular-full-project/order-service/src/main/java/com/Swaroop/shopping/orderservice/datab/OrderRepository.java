package com.Swaroop.shopping.orderservice.datab;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.Swaroop.shopping.orderservice.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends MongoRepository<Order, String> {

    List<Order> findByCustomerId(String customerId);

    Optional<Order> findByOrderId(int orderId);

    Order findTopByOrderByOrderDateDesc();

    Order findTopByOrderByOrderIdDesc();

    Page<Order> findByBuyerEmail(String name, PageRequest request);
}
