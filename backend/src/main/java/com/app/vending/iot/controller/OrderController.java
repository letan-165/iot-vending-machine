package com.app.vending.iot.controller;

import com.app.vending.iot.common.ApiResponse;
import com.app.vending.iot.common.enums.OrderStatus;
import com.app.vending.iot.dto.request.CreateOrderRequest;
import com.app.vending.iot.dto.response.OrderResponse;
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
    public ApiResponse<OrderResponse> getOrder(@PathVariable String id) {
        return ApiResponse.<OrderResponse>builder()
                .result(orderService.getOrder(id))
                .build();
    }

    @GetMapping("/{id}/status")
    public ApiResponse<String> getStatus(@PathVariable String id) {
        return ApiResponse.<String>builder()
                .result(orderService.getStatus(id))
                .build();
    }

    // GUEST
    @PostMapping
    public ApiResponse<String> create(@RequestBody CreateOrderRequest request) {
        return ApiResponse.<String>builder()
                .message("Tạo đơn hàng thành công")
                .result(orderService.create(request))
                .build();
    }

    // STAFF
    @PutMapping("/pending/{id}/status/{status}")
    public ApiResponse<Order> handlePending(@PathVariable String id, @PathVariable OrderStatus status) {
        return ApiResponse.<Order>builder()
                .message("Xử lí đơn hàng thành công")
                .result(orderService.handlePending(id,status))
                .build();
    }

    @PutMapping("/completed/{id}")
    public ApiResponse<Order> completed(@PathVariable String id) {
        return ApiResponse.<Order>builder()
                .message("Hoàn thành đơn hàng")
                .result(orderService.completed(id))
                .build();
    }
}
