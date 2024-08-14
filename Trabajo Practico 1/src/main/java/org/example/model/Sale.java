package org.example.model;

import java.time.LocalDateTime;
import java.util.List;

public record Sale(LocalDateTime dateAndTime, Client client, List<ProductSale> products, double totalPrice) {}