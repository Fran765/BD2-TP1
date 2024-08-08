package org.example.model;

import java.time.LocalDate;
import java.util.List;

public class Venta {
    private LocalDate dateSale;
    private List<Producto> productos;
    private Cliente cliente;

    public Venta(LocalDate dateSale, List<Producto> productos, Cliente cliente) {
        this.dateSale = dateSale;
        this.productos = productos;
        this.cliente = cliente;
    }
}
