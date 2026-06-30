package com.app.vending.iot.entity;

import com.app.vending.iot.common.enums.UserRole;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {

    @Id
    String id;
    String username;
    String password;
    String fullName;
    String email;
    UserRole role;
}
