package com.app.vending.iot.mapper;

import com.app.vending.iot.dto.response.MachineResponse;
import com.app.vending.iot.entity.Machine;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface MachineMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "products", ignore = true)
    void updateMachine(Machine request, @MappingTarget Machine machine);

    @Mapping(source = "products",target = "products", ignore = true)
    MachineResponse toMachineResponse(Machine machine);
}