package ar.unrn.tp.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ProductSale {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer code;
    private String description;
    private String category;
    private Brand brand;
    private float price;

    public ProductSale(float price, Brand brand, String category, String description, Integer code) {
        this.price = price;
        this.brand = brand;
        this.category = category;
        this.description = description;
        this.code = code;
    }

    protected ProductSale() {
    }

}
