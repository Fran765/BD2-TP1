package org.example.infrastructure;

import org.example.model.Card;
import org.example.servcies.ValidatedCard;

public class ByWebValidatedCard implements ValidatedCard {

    @Override
    public boolean validateCard(Card card, double amount) {
        if (!card.isActivate()) {
            return false;
        }

        if (card.sufficientBalance(amount)) {
            return false;
        }

        return true;
    }

}
