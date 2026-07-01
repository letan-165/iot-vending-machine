package com.app.vending.iot.entity;

import com.app.vending.iot.common.enums.ProductStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "products")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product {

    @Id
    String id;
    String machineId;
    String name;
    Double price;
    String image;
    ProductStatus status;
}