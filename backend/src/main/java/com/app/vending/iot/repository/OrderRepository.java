package com.app.vending.iot.repository;

import com.app.vending.iot.common.enums.OrderStatus;
import com.app.vending.iot.entity.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    List<Order> findByStatus(OrderStatus status);
    List<Order> findByDateBetween(LocalDateTime start, LocalDateTime end);

}