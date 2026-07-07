package com.app.vending.iot.dto.response;

import com.app.vending.iot.common.enums.OrderStatus;
import com.app.vending.iot.dto.ProductOrder;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    String id;
    String machineId;
    @Builder.Default
    List<ProductDetailResponse> products = new ArrayList<>();
    LocalDateTime date;
    Double total;
    OrderStatus status;
    LocalDateTime datePay;
}
