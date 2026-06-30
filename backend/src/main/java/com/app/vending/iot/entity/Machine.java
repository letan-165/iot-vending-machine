package com.app.vending.iot.entity;

import com.app.vending.iot.common.enums.MachineStatus;
import com.app.vending.iot.dto.ProductMachine;
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
@Document(collection = "machines")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Machine {

    @Id
    String id;

    @Builder.Default
    List<ProductMachine> products = new ArrayList<>();
    String location;
    MachineStatus status;

}
