package com.Swaroop.shopping.orderservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "address")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class Address {
    @Id
    private String id;
    private int customerId;
    private String fullName;
    private String mobileNumber;
    private int flatNumber;
    private String city;
    private int pinCode;
    private String state;
}
