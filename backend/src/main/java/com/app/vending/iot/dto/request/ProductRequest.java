package com.app.vending.iot.dto.request;

import com.app.vending.iot.common.enums.ProductStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductRequest {
    String name;
    Double price;
    String image;
    ProductStatus status;
}
