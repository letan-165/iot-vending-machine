package com.app.vending.iot.entity;

import com.app.vending.iot.common.enums.PaymentStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "payments")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment {

    @Id
    String id;
    String orderId;
    String method;
    Double amount;
    LocalDateTime date;
    PaymentStatus status;
}