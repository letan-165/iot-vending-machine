package com.app.vending.iot.service;

import com.app.vending.iot.common.exception.AppException;
import com.app.vending.iot.common.exception.ErrorCode;
import com.app.vending.iot.entity.Product;
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

    // GUEST, STAFF, ADMIN
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    // ADMIN
    public Product create(Product product) {

        if (productRepository.existsByName(product.getName()))
            throw new AppException(ErrorCode.PRODUCT_EXISTS);

        return productRepository.save(product);
    }

    // ADMIN
    public Product update(String id, Product request) {

        Product product = productRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

        product = product.toBuilder()
                .name(request.getName())
                .price(request.getPrice())
                .image(request.getImage())
                .quantity(request.getQuantity())
                .status(request.getStatus())
                .build();

        return productRepository.save(product);
    }

    // ADMIN
    public void delete(String id) {

        if(productRepository.existsById(id))
            throw new  AppException(ErrorCode.PRODUCT_NOT_FOUND);

        productRepository.deleteById(id);
    }


}
