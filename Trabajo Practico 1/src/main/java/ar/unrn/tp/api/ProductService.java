package ar.unrn.tp.api;

import java.util.List;

public interface ProductService {
    //validar que sea una categoría existente y que codigo no se repita
    void crearProducto(String codigo, String descripcion, float precio, Long IdCategoría, Long IdBrand);

    //validar que sea un producto existente
    void modificarProducto(Long idProducto, String descripcion, String categoria, String brand, double price);

    //Devuelve todos los productos
    List listarProductos();

}
