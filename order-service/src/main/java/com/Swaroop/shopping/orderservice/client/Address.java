package com.Swaroop.shopping.orderservice.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Address {

    private int houseNumber;
    private String streetName;
    private String colonyName;
    private String city;
    private String state;
    private int pincode;
}
