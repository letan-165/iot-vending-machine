package com.app.vending.iot.dto.request;

import com.app.vending.iot.dto.ProductOrder;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "orders")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateOrderRequest {
    String machineId;
    @Builder.Default
    List<ProductOrderRequest> products = new ArrayList<>();

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProductOrderRequest {
        String productId;
        Integer quantity;
    }
}
