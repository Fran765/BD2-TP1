package ar.unrn.tp.domain;

import ar.unrn.tp.domain.model.Brand;
import ar.unrn.tp.domain.model.BuyDiscount;
import ar.unrn.tp.domain.model.CardType;
import ar.unrn.tp.domain.model.ProductDiscount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Pruebas para la clase Descuentos")
class DiscountsTest {

    @Test
    @DisplayName("Crear un descuento con fechas de validez superpuestas")
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


