package com.app.vending.iot.service;

import com.app.vending.iot.common.enums.ProductLogType;
import com.app.vending.iot.common.exception.AppException;
import com.app.vending.iot.common.exception.ErrorCode;
import com.app.vending.iot.dto.ProductMachine;
import com.app.vending.iot.entity.Machine;
import com.app.vending.iot.mapper.MachineMapper;
import com.app.vending.iot.repository.MachineRepository;
import com.app.vending.iot.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class MachineService {

    MachineRepository machineRepository;
    ProductRepository productRepository;
    MachineMapper machineMapper;
    MachineLogService machineLogService;

    // STAFF, ADMIN
    public List<Machine> getAll() {
        return machineRepository.findAll();
    }

    // ADMIN
    public Machine create(Machine machine) {
        return machineRepository.save(machine);
    }

    // ADMIN
    public Machine update(String id, Machine request) {
        Machine machine = machineRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.MACHINE_NOT_FOUND));

        machineMapper.updateMachine(request, machine);

        if(request.getProducts() != null) {
            Map<String, ProductMachine> productMap = machine.getProducts()
                    .stream()
                    .collect(Collectors.toMap(
                            ProductMachine::getProductId,
                            Function.identity()
                    ));

            for (ProductMachine product : request.getProducts()) {
                if (!productRepository.existsById(product.getProductId()))
                    throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);

                ProductMachine current = productMap.get(product.getProductId());

                if (current != null) {
                    current.setQuantity(product.getQuantity());
                } else {
                    machine.getProducts().add(product);
                }
            }

            machineLogService.updateProduct(machine.getId(), request.getProducts(), ProductLogType.ADJUSTMENT);
        }

        return machineRepository.save(machine);
    }

    // ADMIN
    public void delete(String id) {
        Machine machine = machineRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.MACHINE_NOT_FOUND));

        machineRepository.delete(machine);
    }

    // ADMIN, STAFF
    public Machine importProduct(String id, List<ProductMachine> request) {
        Machine machine = machineRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.MACHINE_NOT_FOUND));

        Map<String, ProductMachine> productMap = machine.getProducts()
                .stream()
                .collect(Collectors.toMap(
                        ProductMachine::getProductId,
                        Function.identity()
                ));

        for (ProductMachine product : request) {
            if (!productRepository.existsById(product.getProductId()))
                throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);

            ProductMachine current = productMap.get(product.getProductId());

            if (current != null) {
                current.setQuantity(product.getQuantity() + current.getQuantity());
            } else {
                machine.getProducts().add(product);
            }
        }

        machineLogService.updateProduct(machine.getId(), request, ProductLogType.IMPORT);
        return machineRepository.save(machine);
    }

}