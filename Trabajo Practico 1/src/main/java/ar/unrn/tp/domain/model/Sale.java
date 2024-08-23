package ar.unrn.tp.domain.model;


import java.time.LocalDateTime;
import java.util.List;

public class Sale {

    private LocalDateTime dateAndTime;
    private Client client;
    private List<ProductSale> products;
    private double totalPrice;

    public Sale(Client client, List<ProductSale> products, double totalPrice) {
        this.dateAndTime = LocalDateTime.now();
        this.client = client;
        this.totalPrice = totalPrice;
        this.products = products;
    }

}