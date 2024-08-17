package org.example.model;

import org.example.service.CardValidateService;
import org.example.service.ProductMapperService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Business {

    private final ProductMapperService productMapperService;
    private final CardValidateService cardValidateService;
    private List<ProductDiscount> productDiscounts;
    private List<BuyDiscount> purchaseDiscounts;

    public Business(ProductMapperService productMapperService, CardValidateService cardValidateService) {
        this.productMapperService = productMapperService;
        this.cardValidateService = cardValidateService;
    }


    public Sale completPurchase(Card card, ShoppingCart cart) {

        double total = cart.totalPriceWithoutDiscount();

        if (!this.productDiscounts.isEmpty())
            total = cart.totalPriceWithDiscount(this.productDiscounts);

        if (!this.purchaseDiscounts.isEmpty())
            total = this.applyDiscounts(card, total);

        if (!this.cardValidateService.validateCard(card, total))
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
                soldProducts.add(productMapperService.convertProductToProductSale(product))
        );

        return soldProducts;
    }

}
