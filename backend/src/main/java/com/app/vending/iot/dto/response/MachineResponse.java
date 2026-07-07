package com.app.vending.iot.dto.response;

import com.app.vending.iot.common.enums.MachineStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MachineResponse {

    String id;

    @Builder.Default
    List<ProductDetailResponse> products = new ArrayList<>();
    String location;
    MachineStatus status;
}

