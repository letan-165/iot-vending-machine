package com.app.vending.iot.controller;

import com.app.vending.iot.common.ApiResponse;
import com.app.vending.iot.entity.Machine;
import com.app.vending.iot.service.MachineService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/machines")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class MachineController {

    MachineService machineService;

    // STAFF, ADMIN
    @GetMapping
    public ApiResponse<List<Machine>> getAll() {
        return ApiResponse.<List<Machine>>builder()
                .result(machineService.getAll())
                .build();
    }

    // ADMIN
    @PostMapping
    public ApiResponse<Machine> create(@RequestBody Machine machine) {
        return ApiResponse.<Machine>builder()
                .message("Thêm máy bán nước thành công")
                .result(machineService.create(machine))
                .build();
    }

    // ADMIN
    @PutMapping("/{id}")
    public ApiResponse<Machine> update(@PathVariable String id,
                                       @RequestBody Machine machine) {
        return ApiResponse.<Machine>builder()
                .message("Cập nhật máy bán nước thành công")
                .result(machineService.update(id, machine))
                .build();
    }

    // ADMIN
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable String id) {

        machineService.delete(id);

        return ApiResponse.<Void>builder()
                .message("Xóa máy bán nước thành công")
                .build();
    }
}