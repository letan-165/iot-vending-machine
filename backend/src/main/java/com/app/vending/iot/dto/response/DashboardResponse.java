package com.app.vending.iot.dto.response;

import com.app.vending.iot.entity.Machine;
import com.app.vending.iot.entity.Order;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DashboardResponse {
    Integer totalRevenueToday;
    Integer totalOrderToday;
    Integer totalProductAvailable;
    Integer totalMachineActive;
    @Builder.Default
    List<Machine> machines = new ArrayList<>();

    @Builder.Default
    List<Order> orderToday = new ArrayList<>();
}
