package org.example.model;

import java.util.Date;

public abstract class Descuento {
    protected float porcentaje;
    protected Date fechaInicio;
    protected Date fechaFin;

    public Descuento(float porcentaje, Date fechaInicio, Date fechaFin) {
        this.porcentaje = porcentaje;
        this.validarFechas(fechaInicio, fechaFin);
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    private void validarFechas(Date fechaInicio, Date fechaFin){
        if(fechaInicio.equals(fechaFin))
            throw new RuntimeException("las fechas del descuento no pueden er iguales");

        if(fechaInicio.after(fechaFin))
            throw new RuntimeException("La fecha de inicio no puede ser mayor que la ficha de fin.");
    }
    public abstract float aplicarDescuento(float price);
    private boolean aplica(){
        return false;
    }

}
