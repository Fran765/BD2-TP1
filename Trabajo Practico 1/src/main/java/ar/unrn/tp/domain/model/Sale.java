package ar.unrn.tp.domain.model;


import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Sale {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime dateAndTime;
    @ManyToOne
    private Client client;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductSale> products;
    private double totalPrice;

    public Sale(Client client, List<ProductSale> products, double totalPrice) {

        this.dateAndTime = LocalDateTime.now();
        this.client = client;
        this.totalPrice = totalPrice;
        this.products = products;
    }

    protected Sale() {
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public LocalDateTime getDateAndTime() {
        return dateAndTime;
    }

    public List<ProductSale> getProducts() {
        return products;
    }

}