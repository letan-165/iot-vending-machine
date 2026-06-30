package com.app.vending.iot.controller;

import com.app.vending.iot.common.ApiResponse;
import com.app.vending.iot.entity.MachineLog;
import com.app.vending.iot.service.MachineLogService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory-logs")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MachineLogController {
    MachineLogService machineLogService;

    // STAFF, ADMIN
    @GetMapping("/{id}")
    public ApiResponse<MachineLog> getById(@PathVariable String id) {
        return ApiResponse.<MachineLog>builder()
                .result(machineLogService.getById(id))
                .build();
    }

}