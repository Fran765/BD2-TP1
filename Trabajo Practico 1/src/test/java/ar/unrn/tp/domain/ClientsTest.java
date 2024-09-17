package ar.unrn.tp.domain;

import ar.unrn.tp.domain.model.Client;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas para la clase cliente")
public class ClientsTest {
    private static void assertException(RuntimeException exception) {
        assertAll(
                () -> assertEquals(RuntimeException.class, exception.getClass()),
                () -> assertEquals("El campo no puede esta vacio.", exception.getMessage())
        );
    }

    @Test
    @DisplayName("Cliente con dni con longitud invalida")
    void test_Create_Client_With_Invalid_Long_Dni() {

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            new Client(12345, "Nombre", "Apellido", "email@example.com", new ArrayList<>());
        });

        assertAll(
                () -> assertEquals(RuntimeException.class, exception.getClass()),
                () -> assertEquals("El DNI debe tener 8 dígitos.", exception.getMessage())
        );
    }

    @Test
    @DisplayName("Crear cliente con nombre en blanco.")
    void test_Create_Client_With_Blank_Name() {

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            new Client(12345678, " ", "Apellido", "email@example.com", new ArrayList<>());
        });

        assertException(exception);
    }

    @Test
    @DisplayName("Crear cliente con apellido en blanco.")
    void test_Create_Client_With_Blank_Surname() {

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            new Client(12345678, "Nombre", " ", "email@example.com", new ArrayList<>());
        });

        assertException(exception);
    }

    @Test
    @DisplayName("Crear cliente con email invalido.")
    void test_Create_Client_With_Invalid_Email() {

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            new Client(12345678, "Nombre", "Apellido", "emailinvalido", new ArrayList<>());
        });

        assertAll(
                () -> assertEquals(RuntimeException.class, exception.getClass()),
                () -> assertEquals("Email debe tener formato valido.", exception.getMessage())
        );
    }

    @Test
    @DisplayName("Crear cliente valido.")
    void test_Create_Client() {

        assertDoesNotThrow(() -> {
            new Client(12345678, "Nombre", "Apellido", "email@example.com", new ArrayList<>());
        });
    }

}
