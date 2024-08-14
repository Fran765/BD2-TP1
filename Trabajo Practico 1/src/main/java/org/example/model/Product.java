package org.example.model;

import java.util.Objects;

public class Product {

    private Integer id;
    private String description;
    private Category category;
    private Brand brand;
    private double price;

    public Product(Integer id, String description, Category category, Brand brand, double price) {
        Objects.requireNonNull(description);
        Objects.requireNonNull(category);
        Objects.requireNonNull(price);

        if(description.isEmpty())
            throw new RuntimeException("La descripcion no puede estar vacia.");

        if(price < 0)
            throw new RuntimeException("El precio no puede ser negativo.");

        this.id = id;
        this.description = description;
        this.category = category;
        this.brand = brand;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public boolean isBrand(Brand aPotentialBrand){
        return this.brand.equals(aPotentialBrand);
    }
}
