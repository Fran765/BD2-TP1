package ar.unrn.tp.infrastructure;

import ar.unrn.tp.domain.model.CreditCard;
import ar.unrn.tp.domain.servcies.ValidatedCard;

public class ByWebValidatedCard implements ValidatedCard {

    @Override
    public boolean validateCard(CreditCard creditCard, double amount) {
        if (!creditCard.isActivate()) {
            return false;
        }

        if (creditCard.sufficientBalance(amount)) {
            return false;
        }

        return true;
    }

}
