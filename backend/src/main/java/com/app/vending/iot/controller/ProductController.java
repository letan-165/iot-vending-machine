package com.app.vending.iot.controller;

import com.app.vending.iot.common.ApiResponse;
import com.app.vending.iot.dto.request.ProductRequest;
import com.app.vending.iot.entity.Product;
import com.app.vending.iot.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class ProductController {
    ProductService productService;

    // GUEST, STAFF, ADMIN
    @GetMapping
    public ApiResponse<List<Product>> getAll() {
        return ApiResponse.<List<Product>>builder()
                .result(productService.getAll())
                .build();
    }

    // ADMIN
    @PostMapping
    public ApiResponse<Product> create(@RequestBody ProductRequest request) {
        return ApiResponse.<Product>builder()
                .message("Thêm sản phẩm thành công")
                .result(productService.create(request))
                .build();
    }

    // ADMIN
    @PutMapping("/{id}")
    public ApiResponse<Product> update(@PathVariable String id,
                                       @RequestBody ProductRequest request) {
        return ApiResponse.<Product>builder()
                .message("Cập nhật sản phẩm thành công")
                .result(productService.update(id, request))
                .build();
    }
}