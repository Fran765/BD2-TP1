package ar.unrn.tp.domain.model;

import java.time.LocalDate;

public class ProductDiscount extends Discount {

    private Brand brand;

    public ProductDiscount(double percent, LocalDate startDate, LocalDate endDate, Brand brand) {
        super(percent, startDate, endDate);
        this.brand = brand;
    }

    public boolean isApply(Product product) {
        return (product.isBrand(this.brand)) && (super.isOnDate());
    }
}
