package ar.unrn.tp.domain;

import ar.unrn.tp.domain.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class VientosPatagonicosTest {

    private Client client;
    private Product product1, product2, product3, product4;


    @BeforeEach
    void pre_set() {

    }

    @Test
    void test_Calculate_Total_Without_Current_Discounts_With_Expired_Discounts() {

    }

    @Test
    void test_Calculate_Total_With_Current_Discount_Acmebrand() {

    }

    @Test
    void test_Calculate_Total_With_Current_Discount_Purchase() {

    }

    @Test
    void test_Calculate_Total_With_Two_Current_Discounts() {

    }

    @Test
    void test_Complet_Purchase_And_Verify_Generated_Sale() {

    }

    @Nested
    @DisplayName("Pruebas para la clase Descuentos")
    class DiscountsTests {

        @Test
        @DisplayName("Pruebas para la clase Descuentos")
        void test_Create_Discount_With_Overlapping_Dates() {

            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                new ProductDiscount(10,
                        LocalDate.now(),
                        LocalDate.now(),
                        Brand.ADIDAS);
            });

            assertEquals("Las fechas del descuento no pueden ser iguales.", exception.getMessage());
        }

        @Test
        @DisplayName("Crear descuentos con fecha de inicio despues de fecha fin.")
        void test_Create_Discount_With_Star_Date_After_End_Date() {
            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                new BuyDiscount(10,
                        LocalDate.of(2024, 11, 16),
                        LocalDate.of(2024, 11, 1),
                        CardType.TARJETA_VISA);
            });

            assertEquals("La fecha de inicio no puede ser mayor que la ficha de fin.", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Pruebas para la clase cliente")
    class ClientTests {

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
        @DisplayName("Dni con dijitos invalidos")
        void test_Create_Client_With_Invalid_Digits() {

            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                new Client(12 - 345 - 678, "Nombre", "Apellido", "email@example.com", new ArrayList<>());
            });

            assertAll(
                    () -> assertEquals(RuntimeException.class, exception.getClass()),
                    () -> assertEquals("El DNI debe contener solo dígitos.", exception.getMessage())
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

    @Nested
    @DisplayName("test para la clase producto")
    class ProductsTests {

        @Test
        @DisplayName("")
        void test_Create_Product_Without_Category_Description_Price() {

            //Prueba sin categoria
            assertThrows(RuntimeException.class, () -> new Product(1, "Description", null, Brand.NIKE, 20));

            //Prueba sin marca
            assertThrows(RuntimeException.class, () -> new Product(1, "Description", Category.REMERAS, null, 20));

        }

        @Test
        @DisplayName("")
        void test_Product_Creation_Empty_Description() {
            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                new Product(1, "", Category.REMERAS, Brand.PUMA, 100);
            });

            assertAll(
                    () -> assertEquals(RuntimeException.class, exception.getClass()),
                    () -> assertEquals("La descripcion no puede estar vacia.", exception.getMessage())
            );
        }

        @Test
        @DisplayName("")
        void test_Product_Creation_Invalid_Price() {
            RuntimeException exception = assertThrows(RuntimeException.class, () -> {
                new Product(1, "Description", Category.REMERAS, Brand.PUMA, 0);
            });

            assertAll(
                    () -> assertEquals(RuntimeException.class, exception.getClass()),
                    () -> assertEquals("El precio no puede ser negativo.", exception.getMessage())
            );
        }
    }
}
