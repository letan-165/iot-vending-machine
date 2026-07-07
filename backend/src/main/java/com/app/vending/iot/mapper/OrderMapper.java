package com.app.vending.iot.mapper;

import com.app.vending.iot.dto.response.OrderResponse;
import com.app.vending.iot.entity.Order;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(source = "products",target = "products", ignore = true)
    OrderResponse toOrderResponse(Order order);
}
