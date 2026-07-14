package com.app.vending.iot.dto.response;

import com.app.vending.iot.common.enums.UserRole;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginResponse {
    String userID;
    UserRole role;
    String token;
}
