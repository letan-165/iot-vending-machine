package com.app.vending.iot.repository;

import com.app.vending.iot.entity.MachineLog;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MachineLogRepository extends MongoRepository<MachineLog, String> {
}