package com.Swaroop.shopping.cartservice.datab;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.Swaroop.shopping.cartservice.entity.Cart;

import java.util.Optional;

@Repository
public interface CartRepository extends MongoRepository<Cart, String> {

    Optional<Cart> findByCartId(int cartId);

    Cart findTopByOrderByCartIdDesc();

    Optional<Cart> findByUser(String email);
}
