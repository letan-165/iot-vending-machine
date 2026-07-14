package com.app.vending.iot.service;

import com.app.vending.iot.common.enums.MachineStatus;
import com.app.vending.iot.common.enums.OrderStatus;
import com.app.vending.iot.dto.ProductMachine;
import com.app.vending.iot.dto.response.DashboardResponse;
import com.app.vending.iot.entity.Machine;
import com.app.vending.iot.entity.Order;
import com.app.vending.iot.repository.MachineRepository;
import com.app.vending.iot.repository.OrderRepository;
import com.app.vending.iot.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DashboardService {

    OrderRepository orderRepository;
    MachineRepository machineRepository;
    ProductRepository productRepository;

    public DashboardResponse getDashboard() {

        // Khoảng thời gian hôm nay
        LocalDate today = LocalDate.now();
        LocalDateTime start = today.atStartOfDay();
        LocalDateTime end = today.atTime(LocalTime.MAX);

        // Lấy đơn hàng hôm nay
        List<Order> ordersToday = orderRepository.findByDateBetween(start, end);

        // Mới nhất trước
        ordersToday.sort(Comparator.comparing(Order::getDate).reversed());

        // Tổng doanh thu hôm nay
        int totalRevenueToday = (int) Math.round(
                ordersToday.stream()
                        .filter(order ->
                                order.getStatus() == OrderStatus.PAID
                                        || order.getStatus() == OrderStatus.COMPLETED)
                        .mapToDouble(Order::getTotal)
                        .sum()
        );

        // Tổng đơn hôm nay
        int totalOrderToday = ordersToday.size();

        // Lấy danh sách máy
        List<Machine> machines = machineRepository.findAll();

        // Tổng số lượng sản phẩm
        int totalProductAvailable = Math.toIntExact(productRepository.count());

        // Tổng số máy đang hoạt động
        int totalMachineActive = (int) machines.stream()
                .filter(machine -> machine.getStatus() == MachineStatus.ACTIVE)
                .count();

        return DashboardResponse.builder()
                .totalRevenueToday(totalRevenueToday)
                .totalOrderToday(totalOrderToday)
                .totalProductAvailable(totalProductAvailable)
                .totalMachineActive(totalMachineActive)
                .machines(machines)
                .orderToday(ordersToday)
                .build();
    }
}