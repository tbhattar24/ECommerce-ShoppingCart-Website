package com.Swaroop.shopping.orderservice.client;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class Cart {
    @Id
    @NotNull
    private String cartId;

    @JsonIgnore
    private String user;

    private Set<ProductInOrder> products = new HashSet<>();

    private String orderId;
    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", products=" + products +
                '}';
    }

    public Cart(String user) {
        this.user  = user;
    }
}
