package org.example.model;

import java.util.List;

public class Carrito {
    private List<Producto> productos;

    public Carrito(List<Producto> productos) {
        this.productos = productos;
    }

    public void agregarProducto(Producto pruducto){
        this.productos.add(pruducto);
    }

    public void quitarProducto(Producto producto){
        try {
            if (!this.productos.isEmpty() && this.productos.contains(producto))
                this.productos.remove(producto);
        } catch (RuntimeException e){
            throw new RuntimeException("No se puede quitar productos porque el carrito no tiene");
        }
    }

    public float totalPricing(){
        float total = 0;
        for (Producto producto : productos) {
            total += producto.getPrice();
        }
        return total;
    }


}
