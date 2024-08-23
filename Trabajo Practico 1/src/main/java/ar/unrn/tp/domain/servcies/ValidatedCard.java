package ar.unrn.tp.domain.servcies;

import ar.unrn.tp.domain.model.CreditCard;

public interface ValidatedCard {

    boolean validateCard(CreditCard creditCard, double amount);
}
