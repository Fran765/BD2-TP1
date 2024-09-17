package ar.unrn.tp.domain;

import ar.unrn.tp.domain.model.Brand;
import ar.unrn.tp.domain.model.Category;
import ar.unrn.tp.domain.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("test para la clase producto.")
public class ProductsTest {
    @Test
    @DisplayName("Crear producto sin marca o sin categoria.")
    void test_Create_Product_Without_Category_Or_Brand() {

        //Prueba sin categoria
        assertThrows(NullPointerException.class, () -> new Product(1, "Description", null, Brand.COMARCA, 20.0));

        //Prueba sin marca
        assertThrows(NullPointerException.class, () -> new Product(1, "Description", Category.REMERAS, null, 20.0));

    }

    @Test
    @DisplayName("Crear producto con descripcion en blanco.")
    void test_Product_Creation_Empty_Description() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            new Product(1, " ", Category.REMERAS, Brand.PUMA, 100.0);
        });

        assertAll(
                () -> assertEquals(RuntimeException.class, exception.getClass()),
                () -> assertEquals("La descripcion no puede estar vacia.", exception.getMessage())
        );
    }

    @Test
    @DisplayName("Crear un producto con precio incorrecto.")
    void test_Product_Creation_Invalid_Price() {

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            new Product(3, "Description", Category.REMERAS, Brand.PUMA, 0.0);
        });

        assertAll(
                () -> assertEquals(RuntimeException.class, exception.getClass()),
                () -> assertEquals("El precio no puede ser igual a 0.0 o negativo.", exception.getMessage())
        );
    }

}
