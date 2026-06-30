package com.app.vending.iot.dto.response;

import com.app.mevocab.common.enums.UserRole;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String userID;
    String username;
    String name;
    String email;
    UserRole role;
}
