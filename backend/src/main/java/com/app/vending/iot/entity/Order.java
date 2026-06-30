package com.app.vending.iot.entity;

import com.app.vending.iot.common.enums.OrderStatus;
import com.app.vending.iot.dto.ProductOrder;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "orders")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {

    @Id
    String id;
    String machineId;
    @Builder.Default
    List<ProductOrder> products = new ArrayList<>();
    LocalDateTime date;
    Double total;
    OrderStatus status;

}
