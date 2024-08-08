package org.example.model;

import java.util.List;
import java.util.Objects;

public class Cliente {
    private Integer dni;
    private String nombre;
    private String apellido;
    private String email;
    private List<Tarjeta> tarjetasCredito;

    public Cliente(Integer dni, String nombre, String apellido, String email, List<Tarjeta> tarjetasCredito) {

        Objects.requireNonNull(dni);
        Objects.requireNonNull(nombre);
        Objects.requireNonNull(apellido);
        Objects.requireNonNull(email);

        if (nombre.isEmpty())
            throw new RuntimeException("El nombre no puede estar vacio.");

        if (apellido.isEmpty())
            throw new RuntimeException("El apellido no puede esta vacio.");

        if (email.isEmpty())
            throw new RuntimeException("Debe ingresar un mail");

        if (!validarMail(email))
            throw new RuntimeException("Email debe ser valido");

        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.tarjetasCredito = tarjetasCredito;
    }

    public void addTarjetaCredito(Tarjeta tarjetaCredito){
        if(this.tarjetasCredito.contains(tarjetaCredito))
            throw new RuntimeException("Esta tarjeta ya existe para este cliente.");
    }

    private boolean validarMail(String mail) {
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return mail.matches(regex);
    }
}
