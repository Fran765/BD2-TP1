package org.example.model;

public class Categoria {

    private Integer id;
    private String nombre;

    public Categoria(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
