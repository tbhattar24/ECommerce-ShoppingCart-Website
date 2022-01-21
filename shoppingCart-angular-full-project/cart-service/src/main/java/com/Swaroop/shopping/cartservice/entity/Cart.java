package com.Swaroop.shopping.cartservice.entity;

import com.Swaroop.shopping.cartservice.dto.ProductInOrder;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Document(collection = "cart")
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
