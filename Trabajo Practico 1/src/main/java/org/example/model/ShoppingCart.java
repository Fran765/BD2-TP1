package org.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ShoppingCart {


    private List<Product> products;
    private Client client;

    public ShoppingCart(Client client) {
        this.products = new ArrayList<Product>();
        this.client = client;
    }

    public void aadProduct(Product pruduct) {
        this.products.add(pruduct);
    }

    public void removeProduct(Product product) {
        try {
            if (!this.products.isEmpty() && this.products.contains(product))
                this.products.remove(product);
        } catch (RuntimeException e) {
            throw new RuntimeException("No se puede quitar productos porque el carrito no tiene");
        }
    }

    public Stream<Product> getProducts() {
        return products.stream();
    }

    public Client ownerOfCart() {
        return this.client;
    }

    public double totalPriceWithDiscount(List<ProductDiscount> discountsProduct) {

        if (this.products.isEmpty())
            throw new RuntimeException("No hay productos en el carrito para calcular el total.");

        double totalPrice = 0.0;

        for (Product product : products) {
            totalPrice += applyDiscounts(product, discountsProduct);
        }
        return totalPrice;
    }

    private double applyDiscounts(Product product, List<ProductDiscount> discountsProduct) {

        double finalPrice = product.getPrice();

        for (ProductDiscount discount : discountsProduct) {
            if ((discount.isOnDate()) && (discount.isApply(product))) {
                return discount.applyDiscount(finalPrice);
            }
        }
        return finalPrice;
    }

    public double totalPriceWithoutDiscount() {

        if (this.products.isEmpty())
            throw new RuntimeException("No hay productos en el carrito para calcular el total.");

        double total = 0.0;
        for (Product product : products) {
            total += product.getPrice();
        }

        return total;
    }

}
