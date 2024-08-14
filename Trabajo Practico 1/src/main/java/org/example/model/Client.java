package org.example.model;

import java.util.List;
import java.util.Objects;

public class Client {
    private Integer dni;
    private String name;
    private String surname;
    private String email;
    private List<Card> cards;
    private static final String REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    public Client(Integer dni, String name, String surname, String email, List<Card> cards) {

        this.validateAttribute(dni.toString());
        this.validateAttribute(name);
        this.validateAttribute(surname);
        this.validateEmail(email);

        this.dni = dni;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.cards = cards;
    }

    public void addCard(Card creditCard){
        if(this.cards.contains(creditCard))
            throw new RuntimeException("Esta tarjeta ya existe para este cliente.");
    }

    private void validateAttribute(String dni){
        Objects.requireNonNull(dni);

        if (dni.isEmpty() || dni.isBlank())
            throw new RuntimeException("El campo no puede esta vacio.");
    }

    private void validateEmail(String email) {
        validateAttribute(email);

        if(email.matches(REGEX))
            throw new RuntimeException("Email debe tener formato valido");
    }

}
