package org.example.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

    private final ProductMapper productMapper;

    private ProductDiscount discountProduct;
    private BuyDiscount discountBuy;
    private Client client;
    private List<Product> products;

    public ShoppingCart(ProductMapper productMapper) {
        this.productMapper = productMapper;
        this.products = new ArrayList<Product>();
    }

    public void aadProduct(Product pruduct){
        this.products.add(pruduct);
    }

    public void removeProduct(Product product){
        try {
            if (!this.products.isEmpty() && this.products.contains(product))
                this.products.remove(product);
        } catch (RuntimeException e){
            throw new RuntimeException("No se puede quitar productos porque el carrito no tiene");
        }
    }

    public double totalPricing(){
        double total = 0.0;

        for (Product product : products) {
            if(this.discountProduct.isApply(product))
                total += this.priceWithDiscount(product);
            else
                total += product.getPrice();
        }

        return total;
    }

    public Sale completPurchase(Card card){
        double total = this.totalPricing();

        if(this.discountBuy.isApply(card))
            total = this.discountBuy.applyDiscount(total);

        return new Sale(LocalDateTime.now(), this.client, this.soldProducts(), total);
    }

    private List<ProductSale> soldProducts(){
        List<ProductSale> soldProducts = new ArrayList<ProductSale>();

        for(Product product: products){
            soldProducts.add(productMapper.convertProductToProductSale(product, priceWithDiscount(product)));
        }

        return soldProducts;
    }

    private double priceWithDiscount(Product product){
        return this.discountProduct.applyDiscount(product.getPrice());
    }

}
