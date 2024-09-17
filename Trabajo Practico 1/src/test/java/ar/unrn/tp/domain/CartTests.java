package ar.unrn.tp.domain;

import ar.unrn.tp.domain.model.*;
import ar.unrn.tp.jpa.services.ProductMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Pruebas para la clase ShoppingCart")
public class CartTests {
    private ProductMapper productMapper;
    private Shop vientosPatagonicos;
    private Client client;
    private ShoppingCart myCart;
    private List<ProductDiscount> productDiscounts = new ArrayList<>();
    private List<BuyDiscount> cardsDiscounts = new ArrayList<>();

    @BeforeEach
    void pre_set() {
        this.client = new Client(42699344,
                "francisco",
                "martin",
                "fran@gmail.com",
                initializeClientCards());

        this.myCart = new ShoppingCart(this.client);

        this.productDiscounts.addAll(this.initializeProductsDiscounts());

        this.cardsDiscounts.addAll(this.initializeCardsDiscounts());

        this.productMapper = Mappers.getMapper(ProductMapper.class);

        this.vientosPatagonicos = new Shop(this.productMapper,
                initializeProductsDiscounts(),
                initializeCardsDiscounts());
    }

    @Test
    @DisplayName("Calcular total sin descuentos vigentes (todos caducados)")
    void test_Calculate_Total_Without_Current_Discounts() {
        List<Product> products = List.of(
                new Product(1, "zapatilla", Category.ZAPATILLAS, Brand.ACME, 200),
                new Product(3, "campera", Category.CAMPERAS, Brand.COMARCA, 600)
        );
        products.forEach(myCart::addProduct);

        double total = myCart.totalPriceWithDiscount(List.of(
                createExpiredProductDiscount(Brand.ACME),
                createExpiredProductDiscount(Brand.COMARCA)
        ));

        assertEquals(800.0, total, 0.01, "El total sin descuentos debería ser 800.");
    }

    @Test
    @DisplayName("Calcular total con descuento actual para marca 'ACME'")
    void test_Calculate_Total_With_Acme_Discount() {
        List<Product> products = List.of(
                new Product(1, "remera", Category.REMERAS, Brand.ACME, 400),
                new Product(4, "pantalon", Category.PANTALONES, Brand.PUMA, 1000)
        );
        products.forEach(myCart::addProduct);

        double total = myCart.totalPriceWithDiscount(this.productDiscounts);
        assertEquals(1368.0, total, 0.01, "El total con descuento ACME es incorrecto.");
    }

    @Test
    @DisplayName("Calcular total con descuento vigente para tarjeta de pago")
    void test_Calculate_Total_With_Card_Discount() {
        List<Product> products = List.of(
                new Product(1, "capera", Category.CAMPERAS, Brand.ADIDAS, 400),
                new Product(4, "remera", Category.REMERAS, Brand.PUMA, 1000)
        );
        products.forEach(myCart::addProduct);

        Sale newSale = vientosPatagonicos.completPurchase(this.myCart, client.getCardByType(CardType.TARJETA_VISA));

        assertAll("Validaciones de la venta con tarjeta VISA",
                () -> assertEquals(1330.0,
                        newSale.getTotalPrice(),
                        0.01,
                        "El precio total con descuento VISA es incorrecto."),

                () -> assertEquals(8670.0,
                        client.getCardByType(CardType.TARJETA_VISA).getFunds(),
                        0.01,
                        "Los fondos no fueron descontados correctamente.")
        );
    }

    @Test
    @DisplayName("Calcular total con descuentos de producto 'COMARCA' y tarjeta 'MEMECARD'.")
    void test_Calculate_Total_With_Product_And_Card_Discounts() {
        List<Product> products = List.of(
                new Product(1, "pantalon", Category.PANTALONES, Brand.COMARCA, 400),
                new Product(4, "zapatillas", Category.ZAPATILLAS, Brand.PUMA, 1000)
        );
        products.forEach(myCart::addProduct);

        Sale newSale = vientosPatagonicos.completPurchase(this.myCart, client.getCardByType(CardType.MEMECARD));

        assertAll("Validaciones de la venta con descuentos de producto y tarjeta",
                () -> assertEquals(1173.0,
                        newSale.getTotalPrice(),
                        0.01,
                        "El precio total con ambos descuentos es incorrecto."),

                () -> assertEquals(8827.0,
                        client.getCardByType(CardType.MEMECARD).getFunds(),
                        0.01,
                        "Los fondos no fueron descontados correctamente.")
        );
    }

    @Test
    @DisplayName("Verificar que se genere la venta correctamente")
    void test_Generate_Sale_Correctly() {
        List<Product> products = List.of(
                new Product(1, "zapatillas", Category.ZAPATILLAS, Brand.ADIDAS, 400),
                new Product(4, "campera", Category.CAMPERAS, Brand.PUMA, 1000)
        );

        products.forEach(myCart::addProduct);

        Sale newSale = vientosPatagonicos.completPurchase(this.myCart, client.getCardByType(CardType.MEMECARD));

        List<ProductSale> expectedProductSales = products.stream()
                .map(this.productMapper::convertProductToProductSale)
                .collect(Collectors.toList());

        assertAll("Validaciones de la venta",
                () -> assertEquals(expectedProductSales,
                        newSale.getProducts(),
                        "Los productos vendidos no coinciden."),

                () -> assertEquals(1190.0,
                        newSale.getTotalPrice(),
                        0.01,
                        "El precio total de la venta es incorrecto."),

                () -> assertTrue(newSale.getDateAndTime().isBefore(LocalDateTime.now().plusSeconds(1)),
                        "La fecha y hora de la venta no es correcta.")
        );
    }

    // Métodos auxiliares
    private List<CreditCard> initializeClientCards() {
        return List.of(
                new CreditCard(12345678L, CardType.MEMECARD),
                new CreditCard(87654321L, CardType.TARJETA_VISA)
        );
    }

    private List<ProductDiscount> initializeProductsDiscounts() {
        return List.of(
                createProductDiscount(8, Brand.ACME),
                createExpiredProductDiscount(Brand.PUMA),
                createProductDiscount(5, Brand.COMARCA),
                createExpiredProductDiscount(Brand.ADIDAS)
        );
    }

    private List<BuyDiscount> initializeCardsDiscounts() {
        return List.of(
                createBuyDiscount(15, CardType.MEMECARD),
                createBuyDiscount(5, CardType.TARJETA_VISA)
        );
    }

    private ProductDiscount createProductDiscount(double percentage, Brand brand) {
        return new ProductDiscount(percentage,
                LocalDate.of(2024, 9, 2),
                LocalDate.now(),
                brand);
    }

    private ProductDiscount createExpiredProductDiscount(Brand brand) {
        return new ProductDiscount(8,
                LocalDate.of(2024, 9, 2),
                LocalDate.of(2024, 9, 4),
                brand);
    }

    private BuyDiscount createBuyDiscount(double percentage, CardType cardType) {
        return new BuyDiscount(percentage,
                LocalDate.of(2024, 9, 4),
                LocalDate.now(),
                cardType);
    }

    /*private List<Product> createProductsList(String... names) {
        List<Product> products = new ArrayList<>();
        for (String name : names) {
            products.add(new Product(1, name, Category.REMERAS, Brand.ACME, 400)); // Ajusta según el nombre
        }
        return products;
    }*/
}

