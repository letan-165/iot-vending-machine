package com.app.vending.iot.repository;

import com.app.vending.iot.entity.MachineLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface MachineLogRepository extends MongoRepository<MachineLog, String> {
    Optional<MachineLog> findByMachineId(String machineId);
}