package org.example.service;

import org.example.model.Product;
import org.example.model.ProductSale;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapperService {

    ProductSale convertProductToProductSale(Product product);
}
