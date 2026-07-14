package com.app.vending.iot.service;

import com.app.vending.iot.common.enums.OrderStatus;
import com.app.vending.iot.common.enums.ProductLogType;
import com.app.vending.iot.common.enums.ProductStatus;
import com.app.vending.iot.common.exception.AppException;
import com.app.vending.iot.common.exception.ErrorCode;
import com.app.vending.iot.dto.ProductLog;
import com.app.vending.iot.dto.ProductMachine;
import com.app.vending.iot.dto.ProductOrder;
import com.app.vending.iot.dto.request.CreateOrderRequest;
import com.app.vending.iot.dto.response.MachineResponse;
import com.app.vending.iot.dto.response.OrderResponse;
import com.app.vending.iot.dto.response.ProductDetailResponse;
import com.app.vending.iot.entity.Machine;
import com.app.vending.iot.entity.Order;
import com.app.vending.iot.entity.Product;
import com.app.vending.iot.mapper.OrderMapper;
import com.app.vending.iot.mapper.ProductMapper;
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
import java.util.ArrayList;
import java.util.Comparator;
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
    OrderMapper orderMapper;
    ProductMapper productMapper;

    // STAFF, ADMIN
    public List<Order> getAll() {
        return orderRepository.findAll().stream()
                .sorted(Comparator.comparing(Order::getDate).reversed())
                .toList();
    }

    // GUEST, STAFF, ADMIN
    public OrderResponse getOrder(String id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        var productOrderIds = order.getProducts().stream()
                .map(ProductOrder::getProductId)
                .toList();

        var products = productRepository.findAllById(productOrderIds);
        Map<String, Product> productMap = products.stream()
                .collect(Collectors.toMap(
                        Product::getId,
                        Function.identity()
                ));

        OrderResponse orderResponse = orderMapper.toOrderResponse(order);

        for (var productOrder : order.getProducts()) {
            Product product = productMap.get(productOrder.getProductId());
            ProductDetailResponse response = productMapper.toProductDetailResponse(product);
            response.setQuantity(productOrder.getQuantity());
            orderResponse.getProducts().add(response);
        }

        return orderResponse;
    }

    public String getStatus(String id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));
        return order.getStatus().name();
    }

    // GUEST
    @Transactional
    public String create(CreateOrderRequest request) {
        if (request.getProducts() == null || request.getProducts().isEmpty())
            throw new AppException(ErrorCode.ORDER_EMPTY);

        Machine machine = machineRepository.findById(request.getMachineId())
                .orElseThrow(()->new AppException(ErrorCode.MACHINE_NOT_FOUND));

        double total = 0;

        Map<String, ProductMachine> productMap = machine.getProducts()
                .stream()
                .collect(Collectors.toMap(
                        ProductMachine::getProductId,
                        Function.identity()
                ));

        List<ProductOrder> productOrders = new ArrayList<>();

        for (CreateOrderRequest.ProductOrderRequest item : request.getProducts()) {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

            ProductMachine current = productMap.get(item.getProductId());
            if (current.getQuantity() < item.getQuantity())
                throw new AppException(ErrorCode.PRODUCT_OUT_OF_STOCK);

            productOrders.add(ProductOrder.builder()
                            .productId(item.getProductId())
                            .quantity(item.getQuantity())
                            .price(product.getPrice())
                    .build());
            total += product.getPrice() * item.getQuantity();
        }

        return orderRepository.save(Order.builder()
                        .machineId(request.getMachineId())
                        .products(productOrders)
                .date(LocalDateTime.now())
                .total(total)
                .status(OrderStatus.PENDING)
                .build()).getId();
    }

    // GUEST
    public Order handlePending(String id, OrderStatus status) {
        if (status.equals(OrderStatus.PENDING))
            throw new AppException(ErrorCode.ORDER_NOT_UPDATE_PENDING);

        if (status.equals(OrderStatus.COMPLETED))
            throw new AppException(ErrorCode.ORDER_NOT_UPDATE_COMPLETED);

        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        OrderStatus orderStatus = order.getStatus();

        if (!orderStatus.equals(OrderStatus.PENDING))
            throw new AppException(ErrorCode.ORDER_NOT_PENDING);

        order.setStatus(status);

        if (status.equals(OrderStatus.PAID))
            order.setDatePay(LocalDateTime.now());

        return orderRepository.save(order);
    }

    // GUEST
    @Transactional
    public Order completed(String id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        OrderStatus orderStatus = order.getStatus();

        if (!orderStatus.equals(OrderStatus.PAID))
            throw new AppException(ErrorCode.ORDER_NOT_PAID);

        Machine machine = machineRepository.findById(order.getMachineId())
                .orElseThrow(()->new AppException(ErrorCode.MACHINE_NOT_FOUND));

        Map<String, ProductMachine> productMap = machine.getProducts()
                .stream()
                .collect(Collectors.toMap(
                        ProductMachine::getProductId,
                        Function.identity()
                ));

        for (ProductOrder item : order.getProducts()) {
            ProductMachine current = productMap.get(item.getProductId());

            if (current.getQuantity() < item.getQuantity())
                throw new AppException(ErrorCode.PRODUCT_OUT_OF_STOCK);

            current.setQuantity(current.getQuantity() - item.getQuantity());
        }

        List<ProductLog> productLogs = order.getProducts()
                .stream()
                .map(p -> {
                    return ProductLog.builder()
                            .id(p.getProductId())
                            .quantity(p.getQuantity())
                            .type(ProductLogType.SALE)
                            .build();
                })
                .toList();

        machineLogService.updateProduct(order.getMachineId(), productLogs);
        machineRepository.save(machine);

        order.setStatus(OrderStatus.COMPLETED);
        return orderRepository.save(order);
    }
}
