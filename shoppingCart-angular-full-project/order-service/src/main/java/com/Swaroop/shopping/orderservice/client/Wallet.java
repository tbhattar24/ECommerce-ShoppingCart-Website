package com.Swaroop.shopping.orderservice.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Wallet {

    private String id;

    private int walletId;

    private String customerId;
    private double currentBalance;
}