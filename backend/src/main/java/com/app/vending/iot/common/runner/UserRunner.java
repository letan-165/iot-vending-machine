//package com.app.vending.iot.common.runner;
//
//import lombok.AccessLevel;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import org.springframework.boot.ApplicationArguments;
//import org.springframework.boot.ApplicationRunner;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//@RequiredArgsConstructor
//@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
//public class UserRunner implements ApplicationRunner {
//    UserRepository userRepository;
//    PasswordEncoder passwordEncoder;
//
//    public record UserSeed(String username, String fullName, UserRole role) {
//    }
//
//    @Override
//    public void run(ApplicationArguments args) throws Exception {
//        List<UserSeed> users = List.of(
//                new UserSeed("tan", "Lê Minh Tân", UserRole.ADMIN),
//                new UserSeed("phu", "Trần Hoàng Phú", UserRole.MANAGER),
//                new UserSeed("dung", "Phan Quốc Dũng", UserRole.STAFF),
//                new UserSeed("cong", "Huỳnh Ngọc Công", UserRole.STAFF),
//                new UserSeed("tuan", "Nguyễn Hoàng Tuấn", UserRole.STAFF),
//
//                new UserSeed("staff1", "Nguyễn staff1", UserRole.STAFF),
//                new UserSeed("staff2", "Nguyễn staff2", UserRole.STAFF),
//                new UserSeed("staff3", "Nguyễn staff3", UserRole.STAFF),
//                new UserSeed("staff4", "Nguyễn staff4", UserRole.STAFF),
//                new UserSeed("staff5", "Nguyễn staff5", UserRole.STAFF)
//
//        );
//
//        users.stream()
//                .filter(u -> userRepository.findByUsername(u.username()).isEmpty())
//                .map(u -> User.builder()
//                        .username(u.username())
//                        .password(passwordEncoder.encode("1"))
//                        .fullName(u.fullName())
//                        .email(u.username+"@email.com")
//                        .status(UserStatus.ACTIVE)
//                        .role(u.role())
//                        .build()
//                )
//                .forEach(userRepository::save);
//    }
//
//}
