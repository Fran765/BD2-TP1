package org.example.model;


import java.time.LocalDate;
import java.util.Date;

public class BuyDiscount extends Discount {

    private CardType cardType;

    public BuyDiscount(LocalDate startDate, LocalDate endDate, CardType cardType, double percent) {
        super(percent, startDate, endDate);
        this.cardType = cardType;
    }

    @Override
    public double applyDiscount(double price) {
        return price * (1 - super.percent / 100);
    }

    public boolean isApply(Card card){
        return (card.isType(this.cardType)) && (super.isOnDate());
    }

}
