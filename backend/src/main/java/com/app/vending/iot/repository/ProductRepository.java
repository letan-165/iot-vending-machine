package com.app.vending.iot.repository;

import com.app.vending.iot.common.enums.ProductStatus;
import com.app.vending.iot.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    boolean existsByName(String name);

    List<Product> findByStatus(ProductStatus status);
    List<Product>findByNameContainingIgnoreCase(String keyword);

}