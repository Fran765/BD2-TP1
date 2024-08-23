package ar.unrn.tp.api;

import java.util.List;

public interface ProductService {
    //validar que sea una categoría existente y que codigo no se repita
    void crearProducto(String codigo, String descripcion, float precio, Long IdCategoría);

    //validar que sea un producto existente
    void modificarProducto(Long idProducto /*datos a modificar...*/);

    //Devuelve todos los productos
    List listarProductos();

}
