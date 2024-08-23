package ar.unrn.tp.domain.model;

public class CreditCard {

    private Long number;
    private CardType type;
    private boolean activate;
    private double funds;

    public CreditCard(Long number, CardType type) {

        this.validateNumbers(String.valueOf(number));

        this.number = number;
        this.type = type;
        this.activate = true;
        this.funds = 0.0;
    }

    private void validateNumbers(String numeroTarjeta) {
        String tarjetaSinEspacios = numeroTarjeta.replaceAll("\\s+", "");
        if (!tarjetaSinEspacios.matches("\\d+") || (tarjetaSinEspacios.length() != 16))
            throw new RuntimeException("Numeros de tarjeta invalidos.");
    }

    public boolean isActivate() {
        return activate;
    }

    public boolean isType(CardType aPotentialType) {
        return this.type.equals(aPotentialType);
    }

    public void addFunds(double funds) {
        this.funds += funds;
    }

    public void subtractFunds(double funds) {
        if (this.funds <= funds)
            throw new RuntimeException("saldo insuficiente en la tarjeta.");
        this.funds -= funds;
    }

    public boolean sufficientBalance(double amount) {
        return (this.funds < amount);
    }


}