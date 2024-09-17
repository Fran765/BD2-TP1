package ar.unrn.tp.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer code;
    private String description;
    private Category category;
    private Brand brand;
    private double price;

    protected Product() {
    }

    public Product(Integer code, String description, Category category, Brand brand, double price) {

        Objects.requireNonNull(category);
        Objects.requireNonNull(brand);

        if (description.isEmpty())
            throw new RuntimeException("La descripcion no puede estar vacia.");

        if (price <= 0.0)
            throw new RuntimeException("El precio no puede ser igual a 0.0 o negativo.");

        this.code = code;
        this.description = description;
        this.category = category;
        this.brand = brand;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public boolean isBrand(Brand aPotentialBrand) {
        return this.brand.equals(aPotentialBrand);
    }

    public void updateDescription(String description) {
        this.description = description;
    }

    public void updateCategory(Category category) {
        this.category = category;
    }

    public void updateBrand(Brand brand) {
        this.brand = brand;
    }

    public void updatePrice(double price) {
        this.price = price;
    }
}
