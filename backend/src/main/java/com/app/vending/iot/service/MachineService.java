package com.app.vending.iot.service;

import com.app.vending.iot.common.enums.ProductLogType;
import com.app.vending.iot.common.exception.AppException;
import com.app.vending.iot.common.exception.ErrorCode;
import com.app.vending.iot.dto.ProductLog;
import com.app.vending.iot.dto.ProductMachine;
import com.app.vending.iot.dto.response.MachineResponse;
import com.app.vending.iot.dto.response.ProductDetailResponse;
import com.app.vending.iot.entity.Machine;
import com.app.vending.iot.entity.Product;
import com.app.vending.iot.mapper.MachineMapper;
import com.app.vending.iot.mapper.ProductMapper;
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
    ProductMapper productMapper;
    MachineLogService machineLogService;

    // STAFF, ADMIN
    public List<Machine> getAll() {
        return machineRepository.findAll();
    }

    // ADMIN
    public Machine create(Machine request) {
        Machine machine = machineRepository.save(request);
        machineLogService.create(machine.getId());
        return machine;
    }

    // GUEST
    public MachineResponse getMachine(String id) {
        Machine machine = machineRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.MACHINE_NOT_FOUND));
        var productMachineIds = machine.getProducts().stream()
                .map(ProductMachine::getProductId)
                .toList();

        var products = productRepository.findAllById(productMachineIds);
        Map<String, Product> productMap = products.stream()
                .collect(Collectors.toMap(
                        Product::getId,
                        Function.identity()
                ));

        MachineResponse machineResponse = machineMapper.toMachineResponse(machine);

        for (var productMachine : machine.getProducts()) {
            Product product = productMap.get(productMachine.getProductId());
            ProductDetailResponse response = productMapper.toProductDetailResponse(product);
            response.setQuantity(productMachine.getQuantity());
            machineResponse.getProducts().add(response);
        }

        return machineResponse;
    }

    // ADMIN
    public Machine update(String id, Machine request) {
        Machine machine = machineRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.MACHINE_NOT_FOUND));

        machineMapper.updateMachine(request, machine);
        return machineRepository.save(machine);
    }

    // ADMIN
    public void delete(String id) {
        Machine machine = machineRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.MACHINE_NOT_FOUND));

        machineRepository.delete(machine);
    }

    // ADMIN, STAFF
    public Machine updateProduct(String id, List<ProductLog> request) {
        Machine machine = machineRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.MACHINE_NOT_FOUND));

        Map<String, ProductMachine> productMap = machine.getProducts()
                .stream()
                .collect(Collectors.toMap(
                        ProductMachine::getProductId,
                        Function.identity()
                ));

        for (ProductLog product : request) {
            if (!productRepository.existsById(product.getId()))
                throw new AppException(ErrorCode.PRODUCT_NOT_FOUND);

            ProductMachine current = productMap.get(product.getId());

            if (current == null) {
                if (product.getType() != ProductLogType.IMPORT)
                    throw new AppException(ErrorCode.REQUEST_TYPE_SALE);

                machine.getProducts().add(ProductMachine.builder()
                                .productId(product.getId())
                                .quantity(product.getQuantity())
                        .build());
                continue;
            }

            switch (product.getType()) {
                case SALE -> throw new AppException(ErrorCode.REQUEST_TYPE_SALE);
                case IMPORT -> current.setQuantity(current.getQuantity() + product.getQuantity());
                case ADJUSTMENT -> current.setQuantity(product.getQuantity());
                case OUTPORT -> machine.getProducts().removeIf(p -> p.getProductId().equals(product.getId()));
            }
        }

        machineLogService.updateProduct(machine.getId(), request);
        return machineRepository.save(machine);
    }



}