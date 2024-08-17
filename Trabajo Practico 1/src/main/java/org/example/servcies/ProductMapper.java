package org.example.servcies;

import org.example.model.Product;
import org.example.model.ProductSale;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {

    ProductSale convertProductToProductSale(Product product);
}
