package com.app.vending.iot.service;

import com.app.vending.iot.common.enums.ProductLogType;
import com.app.vending.iot.common.exception.AppException;
import com.app.vending.iot.common.exception.ErrorCode;
import com.app.vending.iot.dto.ProductLog;
import com.app.vending.iot.dto.ProductMachine;
import com.app.vending.iot.dto.ProductOrder;
import com.app.vending.iot.dto.response.*;
import com.app.vending.iot.entity.Machine;
import com.app.vending.iot.entity.MachineLog;
import com.app.vending.iot.entity.Order;
import com.app.vending.iot.entity.Product;
import com.app.vending.iot.mapper.ProductMapper;
import com.app.vending.iot.repository.MachineLogRepository;
import com.app.vending.iot.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
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
public class MachineLogService {

    MachineLogRepository machineLogRepository;
    ProductRepository productRepository;
    ProductMapper productMapper;

    // STAFF, ADMIN
    public MachineLogResponse getMachineLog(String id) {
        MachineLog machineLog = machineLogRepository.findByMachineId(id)
                .orElseThrow(() -> new AppException(ErrorCode.INVENTORY_LOG_NOT_FOUND));

        var productMachineLogIds = machineLog.getProducts().stream()
                .map(ProductLog::getId)
                .toList();

        var products = productRepository.findAllById(productMachineLogIds);

        Map<String, Product> productMap = products.stream()
                .collect(Collectors.toMap(
                        Product::getId,
                        Function.identity()
                ));

        MachineLogResponse machineLogResponse = MachineLogResponse.builder()
                .id(machineLog.getId())
                .machineId(machineLog.getMachineId())
                .products(new ArrayList<>())
                .build();

        for (var productLog : machineLog.getProducts().stream()
                .sorted(Comparator.comparing(ProductLog::getDate).reversed())
                .toList()) {

            Product product = productMap.get(productLog.getId());
            ProductLogResponse response = productMapper.toProductLogResponse(productLog);
            response.setName(product.getName());
            machineLogResponse.getProducts().add(response);
        }

        return machineLogResponse;
    }

    public void create(String machineId){
        machineLogRepository.save(MachineLog.builder()
                        .machineId(machineId)
                        .products(new ArrayList<>())
                .build());
    }


    // STAFF, ADMIN
    public MachineLog updateProduct(String machineId,List<ProductLog> products) {
        MachineLog machineLog = machineLogRepository.findByMachineId(machineId)
                .orElseThrow(()-> new AppException(ErrorCode.INVENTORY_LOG_NOT_FOUND));

        for (var product : products) {
            product.setDate(LocalDateTime.now());
            machineLog.getProducts().add(product);
        }

        return machineLogRepository.save(machineLog);
    }

}