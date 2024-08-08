package org.example.model;

import java.util.Objects;

public class Producto {

    private Integer id;
    private String description;
    private Categoria categoria;
    private Marca marca;
    private float price;

    public Producto(Integer id, String description, Categoria categoria, Marca marca, float price) {
        Objects.requireNonNull(description);
        Objects.requireNonNull(categoria);
        Objects.requireNonNull(price);

        if(description.isEmpty())
            throw new RuntimeException("La descripcion no puede estar vacia.");

        if(price < 0)
            throw new RuntimeException("El precio no puede ser negativo.");

        this.id = id;
        this.description = description;
        this.categoria = categoria;
        this.marca = marca;
        this.price = price;
    }

    public float getPrice() {
        return price;
    }

    public Marca getMarca(){
        return marca;
    }
}
