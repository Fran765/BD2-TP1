package org.example.servcies;

import org.example.model.Card;

public interface ValidatedCard {

    public boolean validateCard(Card card, double amount);
}
