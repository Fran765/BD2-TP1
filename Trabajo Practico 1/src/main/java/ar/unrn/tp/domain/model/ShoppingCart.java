package ar.unrn.tp.domain.model;

import ar.unrn.tp.domain.servcies.ProductMapper;
import ar.unrn.tp.domain.servcies.ValidatedCard;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ShoppingCart {

    private final ValidatedCard validateCardService;
    private final ProductMapper productMapper;
    private List<Product> products;
    private Client client;

    public ShoppingCart(Client client, ValidatedCard validatedCardService, ProductMapper productMapper) {
        this.products = new ArrayList<Product>();
        this.client = client;
        this.validateCardService = validatedCardService;
        this.productMapper = productMapper;
    }

    public Stream<Product> getProducts() {
        return products.stream();
    }

    public Client ownerOfCart() {
        return this.client;
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

    public double totalPriceWithDiscount(List<ProductDiscount> discountsProduct) {

        this.extracted();

        double totalPrice = this.totalPriceWithoutDiscount();

        for (Product product : products) {
            totalPrice += applyDiscountCard(product, discountsProduct);
        }
        return totalPrice;
    }

    public double totalPriceWithoutDiscount() {

        this.extracted();

        double total = 0.0;
        for (Product product : products) {
            total += product.getPrice();
        }

        return total;
    }

    public Sale completPurchase(List<ProductDiscount> discountsProduct, List<BuyDiscount> purchaseDiscounts, CreditCard card) {

        double totalPrice = this.applyDiscountCard(
                purchaseDiscounts,
                card,
                this.totalPriceWithDiscount(discountsProduct));

        if (!this.validateCardService.validateCard(card, totalPrice))
            throw new RuntimeException("La tarjeta no esta activa o no tiene fondos suficientes");

        card.subtractFunds(totalPrice);
        return new Sale(this.client, this.parseProducts(), totalPrice);

    }

    private double applyDiscountCard(List<BuyDiscount> purchaseDiscounts, CreditCard creditCard, double totalPrice) {

        for (BuyDiscount discount : purchaseDiscounts) {
            if ((discount.isOnDate()) && (discount.isApply(creditCard))) {
                return discount.applyDiscount(totalPrice);
            }
        }
        return totalPrice;
    }

    private double applyDiscountCard(Product product, List<ProductDiscount> discountsProduct) {

        double finalPrice = product.getPrice();

        for (ProductDiscount discount : discountsProduct) {
            if ((discount.isOnDate()) && (discount.isApply(product))) {
                return discount.applyDiscount(finalPrice);
            }
        }
        return finalPrice;
    }

    private List<ProductSale> parseProducts() {
        List<ProductSale> soldProducts = new ArrayList<>();

        products.forEach(product ->
                soldProducts.add(productMapper.convertProductToProductSale(product))
        );

        return soldProducts;
    }

    private void extracted() {
        if (this.products.isEmpty())
            throw new RuntimeException("No hay productos en el carrito para calcular el total.");
    }

}
