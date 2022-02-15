package com.Swaroop.shopping.productservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@Document(collection = "product")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class Product {
    @Id
    private String _id;

    @NotBlank
    private int productId;
    @NotBlank
    private String name;
    @NotBlank
    private String image;
    @NotBlank
    private String brand;
    @NotBlank
    private String description;
    @NotBlank
    private String category;
    @NotBlank
    private Integer numReviews = 0;
    @NotBlank
    private Double price = 0.0;
    
    @NotBlank
    private Integer countInStock = 0;
    private List<Review> reviews;
    @NotBlank
    private Double rating = 0.0;
    @NotBlank
    private String user;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date updatedAt;

    
    @Data
    public static class Review {

        @MongoId
        private String _id;
        private String name;
        private Double rating = 0.0;
        private String comment;
        private String user;
        @CreatedDate
        private Date createdAt;
        @LastModifiedDate
        private Date updatedAt;

        public Review() {
            this._id = ObjectId.get().toHexString();
            this.setCreatedAt(new Date());
        }
    }

}
