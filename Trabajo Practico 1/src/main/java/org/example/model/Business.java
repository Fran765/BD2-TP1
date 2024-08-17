package org.example.model;

import org.example.infrastructure.ByWebValidatedCard;
import org.example.servcies.ProductMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Business {

    private final ProductMapper productMapper;
    private final ByWebValidatedCard byWebCardValidateService;
    private List<ProductDiscount> productDiscounts;
    private List<BuyDiscount> purchaseDiscounts;

    public Business(ProductMapper productMapper, ByWebValidatedCard byWebCardValidateService) {
        this.productMapper = productMapper;
        this.byWebCardValidateService = byWebCardValidateService;
        this.productDiscounts = new ArrayList<>();
        this.purchaseDiscounts = new ArrayList<>();
    }

    public void addProductDiscount(ProductDiscount productDiscount) {
        this.productDiscounts.add(productDiscount);
    }

    public void addBuyDiscount(BuyDiscount buyDiscount) {
        this.purchaseDiscounts.add(buyDiscount);
    }

    public Sale completPurchase(Card card, ShoppingCart cart) {

        double total = cart.totalPriceWithoutDiscount();

        if (!this.productDiscounts.isEmpty())
            total = cart.totalPriceWithDiscount(this.productDiscounts);

        if (!this.purchaseDiscounts.isEmpty())
            total = this.applyDiscounts(card, total);

        if (!this.byWebCardValidateService.validateCard(card, total))
            throw new RuntimeException("La tarjeta no esta activa o no tiene fondos suficientes");

        card.subtractFunds(total);
        return new Sale(LocalDateTime.now(), cart.ownerOfCart(), this.soldProducts(cart), total);
    }

    private double applyDiscounts(Card card, double totalPrice) {

        for (BuyDiscount discount : purchaseDiscounts) {
            if ((discount.isOnDate()) && (discount.isApply(card))) {
                return discount.applyDiscount(totalPrice);
            }
        }
        return totalPrice;
    }

    private List<ProductSale> soldProducts(ShoppingCart cart) {
        List<ProductSale> soldProducts = new ArrayList<>();
        Stream<Product> productsInTheCart = cart.getProducts();

        productsInTheCart.forEach(product ->
                soldProducts.add(productMapper.convertProductToProductSale(product))
        );

        return soldProducts;
    }

}
