package org.example.model;


import java.util.Date;

public class DescuentoVenta extends Descuento {


    public DescuentoVenta(float porcentaje, Date fechaInicio, Date fechaFin) {
        super(porcentaje, fechaInicio, fechaFin);
    }

    @Override
    public float aplicarDescuento(float price) {
        return 0;
    }
}
