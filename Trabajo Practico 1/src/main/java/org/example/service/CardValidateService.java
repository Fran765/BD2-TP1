package org.example.service;

import org.example.model.Card;

public class CardValidateService {

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
