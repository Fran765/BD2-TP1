package ar.unrn.tp.domain.model;

import javax.persistence.*;

@Entity
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long number;
    @Enumerated(EnumType.STRING)
    private CardType type;
    private boolean activate;
    private double funds;

    public CreditCard(Long number, CardType type) {

        this.validateNumbers(String.valueOf(number));

        this.number = number;
        this.type = type;
        this.activate = true;
        this.funds = 10000.0; //hardcode
    }

    protected CreditCard() {
    }

    private void validateNumbers(String numeroTarjeta) {
        String tarjetaSinEspacios = numeroTarjeta.replaceAll("\\s+", "");
        if (!tarjetaSinEspacios.matches("\\d+") || (tarjetaSinEspacios.length() != 8))
            throw new RuntimeException("Numeros de tarjeta invalidos.");
    }

    public void subtractFunds(double funds) {
        if (this.sufficientBalance(funds) && this.activate)
            throw new RuntimeException("Saldo insuficiente en la tarjeta.");
        this.funds -= funds;
    }

    public double getFunds() {
        return funds;
    }

    public boolean isType(CardType aPotentialType) {
        return this.type.equals(aPotentialType);
    }

    private boolean sufficientBalance(double amount) {
        return (this.funds < amount);
    }


}