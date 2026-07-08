package com.app.vending.iot.dto.response;

import com.app.vending.iot.dto.ProductLog;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "inventory_logs")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MachineLogResponse {
    String id;
    String machineId;

    @Builder.Default
    List<ProductLogResponse> products = new ArrayList<>();
}