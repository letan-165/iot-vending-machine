package com.app.vending.iot.service;

import com.app.vending.iot.common.exception.AppException;
import com.app.vending.iot.common.exception.ErrorCode;
import com.app.vending.iot.dto.request.LoginRequest;
import com.app.vending.iot.dto.response.LoginResponse;
import com.app.vending.iot.entity.User;
import com.app.vending.iot.repository.UserRepository;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UserService {

     UserRepository userRepository;
     AuthService authService;
     PasswordEncoder passwordEncoder;

    // ADMIN
    public User create(User user) {
        if (userRepository.existsByUsername(user.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTS);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    // ADMIN
    public User update(String id, User request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        user = user.toBuilder()
                .username(request.getUsername())
                .password(request.getPassword())
                .role(request.getRole())
                .build();

        return userRepository.save(user);
    }

    // ADMIN
    public List<User> getAll() {
        return userRepository.findAll();
    }

    // ADMIN
    public void delete(String id) {
        if (userRepository.existsById(id))
            throw new AppException(ErrorCode.USER_NOT_FOUND);

        userRepository.deleteById(id);
    }

    //GUEST
    public LoginResponse login(LoginRequest request) throws JOSEException {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(()-> new AppException(ErrorCode.USER_NOT_FOUND));

        boolean check = passwordEncoder.matches(request.getPassword(), user.getPassword());

        if(!check)
            throw new AppException(ErrorCode.PASSWORD_INVALID);

        return LoginResponse.builder()
                .userID(user.getId())
                .fullName(user.getFullName())
                .token(authService.generate(user))
                .build();
    }

}