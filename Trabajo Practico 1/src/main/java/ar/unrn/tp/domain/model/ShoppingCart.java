package ar.unrn.tp.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ShoppingCart {

    private List<Product> products;
    private Client client;

    public ShoppingCart(Client client) {
        this.products = new ArrayList<>();
        this.client = client;
    }

    public ShoppingCart() {
        this.products = new ArrayList<>();
    }

    public Stream<Product> getProducts() {
        return products.stream();
    }

    public Client ownerOfCart() {
        return this.client;
    }

    public void addProduct(Product pruduct) {
        this.products.add(pruduct);
    }


    public double totalPriceWithDiscount(List<ProductDiscount> discountsProduct) {

        this.productsIsEmpty();

        if (discountsProduct.isEmpty())
            return this.totalPriceWithoutDiscount();

        return products.stream().mapToDouble(p ->
                applyDiscountProduct(p, discountsProduct)).sum();
    }

    public double totalPriceWithoutDiscount() {
        this.productsIsEmpty();
        return products.stream().mapToDouble(p -> p.getPrice()).sum();
    }

    private double applyDiscountProduct(Product product, List<ProductDiscount> discountsProduct) {

        double finalPrice = product.getPrice();

        for (ProductDiscount discount : discountsProduct) {
            if ((discount.isOnDate()) && (discount.isApply(product))) {
                return discount.applyDiscount(finalPrice);
            }
        }
        return finalPrice;
    }

    private void productsIsEmpty() {
        if (this.products.isEmpty())
            throw new RuntimeException("No hay productos en el carrito para calcular el total.");
    }

}
