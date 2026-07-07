package com.app.vending.iot.mapper;

import com.app.vending.iot.dto.response.ProductDetailResponse;
import com.app.vending.iot.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDetailResponse toProductDetailResponse(Product product);
}
