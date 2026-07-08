package com.app.vending.iot.service;

import com.app.vending.iot.common.exception.AppException;
import com.app.vending.iot.common.exception.ErrorCode;
import com.app.vending.iot.dto.request.ProductRequest;
import com.app.vending.iot.entity.Product;
import com.app.vending.iot.mapper.ProductMapper;
import com.app.vending.iot.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ProductService {

    ProductRepository productRepository;
    ProductMapper productMapper;

    // GUEST, STAFF, ADMIN
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    // ADMIN
    public Product create(ProductRequest request) {

        if (productRepository.existsByName(request.getName()))
            throw new AppException(ErrorCode.PRODUCT_EXISTS);

        return productRepository.save(productMapper.toProduct(request));
    }

    // ADMIN
    public Product update(String id, ProductRequest request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        productMapper.updateProduct(request, product);

        return productRepository.save(product);
    }

}
