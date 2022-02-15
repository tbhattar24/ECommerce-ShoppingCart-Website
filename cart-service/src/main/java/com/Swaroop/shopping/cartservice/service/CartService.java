package com.Swaroop.shopping.cartservice.service;

import lombok.AllArgsConstructor;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Swaroop.shopping.cartservice.client.Order;
import com.Swaroop.shopping.cartservice.client.OrderServiceClient;
import com.Swaroop.shopping.cartservice.datab.CartRepository;
import com.Swaroop.shopping.cartservice.dto.ProductInOrder;
import com.Swaroop.shopping.cartservice.entity.Cart;
import com.Swaroop.shopping.cartservice.entity.Items;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class CartService {

    private final CartRepository cartRepository;
    private final OrderServiceClient orderServiceClient;

    public Cart getCartById(int cartId) {
        return cartRepository.findByCartId(cartId).orElse(new Cart());
    }

    public List<Cart> getAllCart() {
        return cartRepository.findAll();
    }

    public Cart getCart(String email) {
        return cartRepository.findByUser(email).orElse(new Cart(email));
    }


    @Transactional
    public void mergeLocalCart(Set<ProductInOrder> productInOrders, String user) {
        Cart finalCart = getCart(user);
        Set<ProductInOrder> products = finalCart.getProducts();
        log.info("Before ::" + products);
        productInOrders.stream().forEach(productInOrder -> products.add(productInOrder));
        log.info("After " + products);
        finalCart.setProducts(products);
        cartRepository.save(finalCart);

    }

    public String checkout(Authentication auth) {
        ResponseEntity<Order> orderResponseEntity = orderServiceClient.makeOrder(auth.getCredentials().toString());
        if (orderResponseEntity.getStatusCode() == HttpStatus.CREATED) {
            Cart finalCart = getCart(auth.getName());
            cartRepository.delete(finalCart);
            return  orderResponseEntity.getBody().get_id();
        }
        return "";
    }
}
