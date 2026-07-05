package com.app.vending.iot.controller;

import com.app.vending.iot.common.ApiResponse;
import com.app.vending.iot.common.enums.OrderStatus;
import com.app.vending.iot.entity.Order;
import com.app.vending.iot.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class OrderController {

    OrderService orderService;

    // STAFF, ADMIN
    @GetMapping
    public ApiResponse<List<Order>> getAll() {
        return ApiResponse.<List<Order>>builder()
                .result(orderService.getAll())
                .build();
    }

    // GUEST, STAFF, ADMIN
    @GetMapping("/{id}")
    public ApiResponse<Order> getById(@PathVariable String id) {
        return ApiResponse.<Order>builder()
                .result(orderService.getById(id))
                .build();
    }

    // GUEST
    @PostMapping
    public ApiResponse<Order> create(@RequestBody Order order) {
        return ApiResponse.<Order>builder()
                .message("Tạo đơn hàng thành công")
                .result(orderService.create(order))
                .build();
    }

    // STAFF
    @PutMapping("/{id}/status/{status}")
    public ApiResponse<Order> updateStatus(@PathVariable String id, @PathVariable OrderStatus status) {
        return ApiResponse.<Order>builder()
                .message("Thay đổi trạng thái đơn hàng thành công")
                .result(orderService.updateStatus(id,status))
                .build();
    }
}
