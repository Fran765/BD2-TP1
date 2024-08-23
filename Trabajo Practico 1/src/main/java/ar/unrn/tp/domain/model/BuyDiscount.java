package ar.unrn.tp.domain.model;


import java.time.LocalDate;

public class BuyDiscount extends Discount {

    private CardType cardType;

    public BuyDiscount(double percent, LocalDate startDate, LocalDate endDate, CardType cardType) {
        super(percent, startDate, endDate);
        this.cardType = cardType;
    }

    public boolean isApply(CreditCard creditCard) {
        return (creditCard.isType(this.cardType)) && (super.isOnDate());
    }

}
