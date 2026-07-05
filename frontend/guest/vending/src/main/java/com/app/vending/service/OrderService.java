package com.app.vending.service;

import com.app.vending.model.Item;
import com.app.vending.model.Order;
import com.app.vending.model.OrderItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service xử lý logic tạo đơn hàng.
 */
public class OrderService {

    private final List<Order> orderHistory = new ArrayList<>();

    /**
     * Tạo đơn hàng từ danh sách Item có quantity > 0.
     * @throws IllegalArgumentException nếu không có sản phẩm nào được chọn.
     */
    public Order createOrder(List<Item> items) {
        List<OrderItem> selected = items.stream()
                .filter(i -> i.getQuantity() > 0)
                .map(OrderItem::new)
                .collect(Collectors.toList());

        if (selected.isEmpty()) {
            throw new IllegalArgumentException("Vui lòng chọn ít nhất một sản phẩm!");
        }

        Order order = new Order(selected);
        orderHistory.add(order);
        return order;
    }

    public List<Order> getOrderHistory() {
        return List.copyOf(orderHistory);
    }
}
