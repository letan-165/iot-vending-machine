package com.app.vending.iot.repository;

import com.app.vending.iot.common.enums.MachineStatus;
import com.app.vending.iot.entity.Machine;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MachineRepository extends MongoRepository<Machine, String> {
    List<Machine> findByStatus(MachineStatus status);
}