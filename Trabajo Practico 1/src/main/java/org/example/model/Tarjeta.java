package org.example.model;

public class Tarjeta {
    private Integer numero;
    private TipoTarjeta tipo;
    private boolean activada;
    private float saldo;

    public Tarjeta(Integer numero, TipoTarjeta tipo, boolean activada, float saldo) {
        this.numero = numero;
        this.tipo = tipo;
        this.activada = activada;
        this.saldo = saldo;
    }

    public boolean isActivada() {
        return activada;
    }

    public TipoTarjeta getTipo() {
        return tipo;
    }

    public void addSaldo(float saldo){
        this.saldo += saldo;
    }

    public void subtractSaldo(float saldo){
        if(this.saldo <= saldo)
            throw new RuntimeException("saldo insuficiente en la tarjeta.");
        this.saldo -= saldo;
    }
}