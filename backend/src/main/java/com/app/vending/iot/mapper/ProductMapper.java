package com.app.vending.iot.mapper;

import com.app.vending.iot.dto.ProductLog;
import com.app.vending.iot.dto.request.ProductRequest;
import com.app.vending.iot.dto.response.ProductDetailResponse;
import com.app.vending.iot.dto.response.ProductLogResponse;
import com.app.vending.iot.entity.Product;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toProduct(ProductRequest request);
    ProductDetailResponse toProductDetailResponse(Product product);
    ProductLogResponse toProductLogResponse(ProductLog product);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProduct(ProductRequest request, @MappingTarget Product product);
}
