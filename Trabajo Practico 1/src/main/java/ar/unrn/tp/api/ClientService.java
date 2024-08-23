package ar.unrn.tp.api;

import java.util.List;

public interface ClientService {
    // validar que el dni no se repita
    void crearCliente(String nombre, String apellido, String dni, String email);

    // validar que sea un cliente existente
    void modificarCliente(Long idCliente /*datos a modificar...*/);

    // validar que sea un cliente existente
    void agregarTarjeta(Long idCliente, String nro, String marca);

    //Devuelve las tarjetas de un cliente específico
    List listarTarjetas(Long idCliente);
}
