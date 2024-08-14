package org.example.model;

public class Card {
    private Integer number;
    private CardType type;
    private boolean activate;
    private float funds;

    public Card(Integer number, CardType type, boolean activate, float funds) {
        this.number = number;
        this.type = type;
        this.activate = activate;
        this.funds = funds;
    }

    public boolean isActivate() {
        return activate;
    }

    public boolean isType(CardType aPotentialType) {
        return this.type.equals(aPotentialType);
    }

    public void addFunds(float funds){
        this.funds += funds;
    }

    public void subtractFunds(float funds){
        if(this.funds <= funds)
            throw new RuntimeException("saldo insuficiente en la tarjeta.");
        this.funds -= funds;
    }
}