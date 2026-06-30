package com.app.vending.iot.controller;

import com.app.vending.iot.entity.User;
import com.app.vending.iot.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.app.vending.iot.common.ApiResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserController {
    UserService userService;

    // ADMIN
    @GetMapping
    public ApiResponse<List<User>> getAll() {
        return ApiResponse.<List<User>>builder()
                .result(userService.getAll())
                .build();
    }

    // ADMIN
    @PostMapping
    public ApiResponse<User> create(@RequestBody User user) {
        return ApiResponse.<User>builder()
                .message("Tạo người dùng thành công")
                .result(userService.create(user))
                .build();
    }

    // ADMIN
    @PutMapping("/{id}")
    public ApiResponse<User> update(@PathVariable String id,
                                    @RequestBody User user) {
        return ApiResponse.<User>builder()
                .message("Cập nhật người dùng thành công")
                .result(userService.update(id, user))
                .build();
    }

    // ADMIN
    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable String id) {
        userService.delete(id);

        return ApiResponse.<Void>builder()
                .message("Xóa người dùng thành công")
                .build();
    }
}
