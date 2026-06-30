package com.app.vending.iot.dto;

import com.app.vending.iot.common.enums.ProductLogType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductLog {
    String productId;
    Integer quantity;
    ProductLogType type;
    LocalDateTime date;
}
