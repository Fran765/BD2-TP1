package ar.unrn.tp.domain.servcies;

import ar.unrn.tp.domain.model.Product;
import ar.unrn.tp.domain.model.ProductSale;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {

    ProductSale convertProductToProductSale(Product product);
}
