package com.Swaroop.shopping.productservice.service;

import lombok.AllArgsConstructor;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Swaroop.shopping.productservice.database.ProductRepository;
import com.Swaroop.shopping.productservice.entity.Product;
import com.Swaroop.shopping.productservice.entity.TopProductRes;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public void addProduct(Product product) {
        product.setProductId(getNextId());
        productRepository.save(product);
    }

    public Product updateProduct(Product product) {
        Optional<Product> byProductId = productRepository.findByProductId(product.getProductId());
        if (byProductId.isPresent()) {
            product.set_id(byProductId.get().get_id());
            return productRepository.save(product);
        } else {
            throw  new IllegalArgumentException("No such product found");
        }
    }

    public void deleteProductById(int productId) {
        Optional<Product> byProductId = productRepository.findByProductId(productId);
        if (byProductId.isPresent())
            productRepository.deleteById(byProductId.get().get_id());
    }
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
    public Page<Product> getProductsByCategory(String category, Pageable pageable) {
        return productRepository.findByCategory(category, pageable);
    }

    public Optional<Product> getProductById(int productId) {
        return productRepository.findByProductId(productId);
    }

    public Optional<Product> getProductByName(String productName) {
        return productRepository.findByName(productName);
    }

    @Synchronized
    public int getNextId() {
        Product product = productRepository.findTopByOrderByProductIdDesc();
        int id = (product != null) ? product.getProductId() : 0;
        return ++id;
    }

    public Stream<TopProductRes> getTopProducts() {
        return productRepository.getTopProducts();
    }

    public Page<Product> findAllByQ(String key, Pageable pageable) {
        return productRepository.findAllByQ(key, pageable);
    }

    public Optional<Product> findById(String id) {
        return productRepository.findById(id);
    }
}
