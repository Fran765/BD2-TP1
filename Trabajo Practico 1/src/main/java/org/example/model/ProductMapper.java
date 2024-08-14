package org.example.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ProductMapper {

    @Mapping(source = "precio", target = "precioVenta")
    ProductSale convertProductToProductSale(Product product, double precioVenta);
}
