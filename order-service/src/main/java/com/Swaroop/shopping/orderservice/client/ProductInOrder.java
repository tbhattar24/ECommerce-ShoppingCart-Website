package com.Swaroop.shopping.orderservice.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductInOrder {
    @NotEmpty
    private String _id;

    @NotEmpty
    private String name;

    @NotNull
    private String description;

    private String image;

    private Integer categoryType;

    @NotNull
    private BigDecimal price;

    @Min(0)
    private Integer countInStock;

    @Min(1)
    private Integer count;

}