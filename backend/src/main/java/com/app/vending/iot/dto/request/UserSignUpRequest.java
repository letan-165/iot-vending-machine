package com.app.vending.iot.dto.request;

import com.app.vending.iot.common.enums.UserRole;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserSignUpRequest {
    String username;
    String password;
    String fullName;
    String email;
    UserRole role;
}
