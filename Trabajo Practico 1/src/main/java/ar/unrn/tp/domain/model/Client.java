package ar.unrn.tp.domain.model;

import ar.unrn.tp.utils.Email;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer dni;
    private String name;
    private String surname;
    private String email;

    @OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_client")
    private List<CreditCard> creditCards;

    public Client(Integer dni, String name, String surname, String email, List<CreditCard> creditCards) {

        this.validateDni(dni);
        this.validateAttribute(name);
        this.validateAttribute(surname);

        this.dni = dni;
        this.name = name;
        this.surname = surname;
        this.email = new Email(email).asString();
        this.creditCards = creditCards;
    }

    protected Client() {
    }

    public CreditCard getCardByType(CardType type) {
        return creditCards.stream()
                .filter(card -> card.isType(type))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No se encontró una tarjeta de tipo: " + type));
    }

    public void updateData(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public boolean isMyCard(CreditCard aPotentialCard) {
        if (aPotentialCard == null)
            throw new IllegalArgumentException("La tarjeta no puede ser nula.");

        return creditCards.contains(aPotentialCard);
    }

    public void addCreditCard(CreditCard newCard) {
        this.creditCards.add(newCard);
    }

    public Stream<CreditCard> getCreditCards() {
        return this.creditCards.stream();
    }

    private void validateAttribute(String attribute) {
        Objects.requireNonNull(attribute);

        if (attribute.isEmpty() || attribute.isBlank())
            throw new RuntimeException("El campo no puede esta vacio.");
    }

    private void validateDni(int dni) {
        String dniStr = String.valueOf(dni);
        if (dniStr.length() != 8) {
            throw new RuntimeException("El DNI debe tener 8 dígitos.");
        }

    }

}
