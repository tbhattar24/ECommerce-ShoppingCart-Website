package com.Swaroop.shopping.walletservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "walletStatement")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class Statement {
    @Id
    private String id;

    private int statementId;
    private int walletId;
    private double amount;
    private LocalDate date;
    private int orderId;
    private String transactionRemark;
}
