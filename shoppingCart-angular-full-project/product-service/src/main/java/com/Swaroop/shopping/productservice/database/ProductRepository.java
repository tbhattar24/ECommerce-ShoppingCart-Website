package com.Swaroop.shopping.productservice.database;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.Swaroop.shopping.productservice.entity.Product;
import com.Swaroop.shopping.productservice.entity.TopProductRes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
	
	  Optional<Product> findByName(String productName); Optional<Product>
	  findByProductId(int productId);
	  
	  Page<Product> findByCategory(String category, Pageable pageable);
	  
	  Product findTopByOrderByProductIdDesc();
	  
	  @Query(value="{}", sort="{rating: -1}", fields =
	  "{ _id: 1 , name: 1, image: 1, price:1 }") Stream<TopProductRes>
	  getTopProducts();
	  
	  @Query(value = "{name: {$regex: ?0, $options: 'i'}}") Page<Product>
	  findAllByQ(String query, Pageable pageable);
	 
}
