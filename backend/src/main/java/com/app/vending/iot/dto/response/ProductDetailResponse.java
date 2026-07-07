package com.app.vending.iot.dto.response;

import com.app.vending.iot.common.enums.ProductStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDetailResponse {
    String id;
    String name;
    Double price;
    String image;
    ProductStatus status;

    Integer quantity;
}
