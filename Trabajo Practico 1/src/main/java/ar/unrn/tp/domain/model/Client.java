package ar.unrn.tp.domain.model;

import java.util.List;
import java.util.Objects;

public class Client {
    private static final String REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private Integer dni;
    private String name;
    private String surname;
    private String email;
    private List<CreditCard> creditCards;

    public Client(Integer dni, String name, String surname, String email, List<CreditCard> creditCards) {

        this.validateDni(dni);
        this.validateAttribute(name);
        this.validateAttribute(surname);
        this.validateEmail(email);

        this.dni = dni;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.creditCards = creditCards;
    }

    public void addCard(CreditCard creditCard) {
        if (this.creditCards.contains(creditCard))
            throw new RuntimeException("Esta tarjeta ya existe para este cliente.");
    }

    private void validateAttribute(String attribute) {
        Objects.requireNonNull(attribute);

        if (attribute.isEmpty() || attribute.isBlank())
            throw new RuntimeException("El campo no puede esta vacio.");
    }

    private void validateEmail(String email) {
        validateAttribute(email);

        if (!email.matches(REGEX))
            throw new RuntimeException("Email debe tener formato valido.");
    }

    private void validateDni(int dni) {
        String dniStr = String.valueOf(dni);
        if (dniStr.length() != 8) {
            throw new RuntimeException("El DNI debe tener 8 dígitos.");
        }

    }

}
