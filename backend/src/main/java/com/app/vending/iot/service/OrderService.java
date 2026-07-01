package com.app.vending.iot.service;

import com.app.vending.iot.common.enums.OrderStatus;
import com.app.vending.iot.common.enums.ProductLogType;
import com.app.vending.iot.common.exception.AppException;
import com.app.vending.iot.common.exception.ErrorCode;
import com.app.vending.iot.dto.ProductMachine;
import com.app.vending.iot.dto.ProductOrder;
import com.app.vending.iot.entity.Machine;
import com.app.vending.iot.entity.Order;
import com.app.vending.iot.entity.Product;
import com.app.vending.iot.repository.MachineRepository;
import com.app.vending.iot.repository.OrderRepository;
import com.app.vending.iot.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class OrderService {

    OrderRepository orderRepository;
    ProductRepository productRepository;
    MachineRepository machineRepository;
    MachineLogService machineLogService;

    // STAFF, ADMIN
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    // GUEST, STAFF, ADMIN
    public Order getById(String id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
    }

    // GUEST
    @Transactional
    public Order create(Order order) {

        if (order.getProducts() == null || order.getProducts().isEmpty())
            throw new AppException(ErrorCode.ORDER_EMPTY);

        Machine machine = machineRepository.findById(order.getMachineId())
                .orElseThrow(()->new AppException(ErrorCode.MACHINE_NOT_FOUND));

        double total = 0;

        Map<String, ProductMachine> productMap = machine.getProducts()
                .stream()
                .collect(Collectors.toMap(
                        ProductMachine::getProductId,
                        Function.identity()
                ));

        for (ProductOrder item : order.getProducts()) {
            item.setPrice(0.0);//Late

            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

            ProductMachine current = productMap.get(item.getProductId());
            if (current.getQuantity() < item.getQuantity())
                throw new AppException(ErrorCode.PRODUCT_OUT_OF_STOCK);

            item.setPrice(product.getPrice());
            total += product.getPrice() * item.getQuantity();

            current.setQuantity(current.getQuantity() - item.getQuantity());
        }

        order = order.toBuilder()
                .date(LocalDateTime.now())
                .total(total)
                .status(OrderStatus.PENDING)
                .build();

        List<ProductMachine> productMachines = order.getProducts()
                .stream()
                .map(p -> {
                    ProductMachine pm = new ProductMachine();
                    pm.setProductId(p.getProductId());
                    pm.setQuantity(p.getQuantity());
                    return pm;
                })
                .toList();

        machineLogService.updateProduct(order.getMachineId(), productMachines, ProductLogType.SALE);
        machineRepository.save(machine);
        return orderRepository.save(order);
    }

    // STAFF
    public Order updateStatus(String id, OrderStatus status) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        order.setStatus(status);

        return orderRepository.save(order);
    }
}
