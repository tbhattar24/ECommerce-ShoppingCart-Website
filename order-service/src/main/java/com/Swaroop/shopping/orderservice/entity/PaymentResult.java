package com.Swaroop.shopping.orderservice.entity;

import lombok.Data;

@Data
public class PaymentResult {

    private String id;
    private String status;
    private String updateTime;
    private String emailAddress;
}
