package com.app.vending.iot.service;

import com.app.vending.iot.common.enums.ProductLogType;
import com.app.vending.iot.common.exception.AppException;
import com.app.vending.iot.common.exception.ErrorCode;
import com.app.vending.iot.dto.ProductLog;
import com.app.vending.iot.dto.ProductMachine;
import com.app.vending.iot.entity.Machine;
import com.app.vending.iot.entity.MachineLog;
import com.app.vending.iot.repository.MachineLogRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class MachineLogService {

    MachineLogRepository machineLogRepository;

    // STAFF, ADMIN
    public MachineLog getById(String id) {
        return machineLogRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.INVENTORY_LOG_NOT_FOUND));
    }

    // STAFF, ADMIN
    public MachineLog updateProduct(String id,List<ProductMachine> products, ProductLogType type) {
        MachineLog machineLog = machineLogRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.INVENTORY_LOG_NOT_FOUND));

        for (var product : products) {
            machineLog.getProducts().add(ProductLog.builder()
                            .productId(product.getProductId())
                            .quantity(product.getQuantity())
                            .type(type)
                            .date(LocalDateTime.now())
                    .build());
        }

        return machineLogRepository.save(machineLog);
    }

}