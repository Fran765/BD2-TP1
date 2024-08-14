package org.example.model;

import java.time.LocalDate;

public class ProductDiscount extends Discount {

    private Brand brand;

    public ProductDiscount(LocalDate startDate, LocalDate endDate, Brand brand, double percent) {
        super(percent, startDate, endDate);
        this.brand = brand;
    }

    @Override
    public double applyDiscount(double price) {
        return price * (1 - super.percent / 100);
    }

    public boolean isApply(Product product){
        return (product.isBrand(this.brand)) && (super.isOnDate());
    }
}
