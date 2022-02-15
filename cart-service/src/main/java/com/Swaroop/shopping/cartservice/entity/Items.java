package com.Swaroop.shopping.cartservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Items {

    private String productName;
    private double price;
    private int quantity;
}
