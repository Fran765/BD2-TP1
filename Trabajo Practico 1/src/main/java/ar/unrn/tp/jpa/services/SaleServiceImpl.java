package ar.unrn.tp.jpa.services;

import ar.unrn.tp.api.SaleService;
import ar.unrn.tp.api.TransactionService;
import ar.unrn.tp.domain.model.*;
import lombok.RequiredArgsConstructor;

import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {

    private final TransactionService transactionService;
    private final Shop shop;

    @Override
    public void realizarVenta(Long idCliente, List<Long> productos, Long idTarjeta) {

        if (idCliente == null)
            throw new RuntimeException("El id del cliente no pueden ser nulos.");

        CreditCard card = this.obtenerTarjeta(idTarjeta);
        List<Product> productList = this.obtenerProductosSeleccionados(productos);
        ShoppingCart cart = crearCarritoConProductos(productList);

        this.transactionService.executeInTransaction(em -> {

            Client client = em.find(Client.class, idCliente);

            if (client == null)
                throw new RuntimeException("Cliente no encontrado.");

            if (!client.isMyCard(card))
                throw new RuntimeException("La tarjeta no corresponde para este cliente.");

            em.persist(shop.completPurchase(cart, card));
        });
    }

    @Override
    public double calcularMonto(List<Long> productos, Long idTarjeta) {

        CreditCard card = this.obtenerTarjeta(idTarjeta);
        List<Product> productList = this.obtenerProductosSeleccionados(productos);
        ShoppingCart cart = crearCarritoConProductos(productList);

        return shop.calcularTotal(cart, card);
    }

    @Override
    public List<Sale> ventas() {

        List<Sale> ventas = new ArrayList<>();

        this.transactionService.executeInTransaction(em -> {

            TypedQuery<Sale> sql = em.createQuery("SELECT s FROM Sale s", Sale.class);

            ventas.addAll(sql.getResultList());

            if (ventas.isEmpty())
                throw new RuntimeException("No hay ventas en la base.");

        });

        return ventas;
    }

    private CreditCard obtenerTarjeta(Long idTarjeta) {

        if (idTarjeta == null)
            throw new RuntimeException("La tarjeta no puede estar vacia.");

        CreditCard[] creditCardHolder = new CreditCard[1];

        this.transactionService.executeInTransaction(em -> {

            CreditCard creditCard = em.find(CreditCard.class, idTarjeta);

            if (creditCard == null)
                throw new RuntimeException("Tarjeta no válida.");

            creditCardHolder[0] = creditCard;
        });

        return creditCardHolder[0];
    }

    private List<Product> obtenerProductosSeleccionados(List<Long> idProducts) {

        if (idProducts.isEmpty())
            throw new RuntimeException("La lista de refencia de productos esta vacia.");

        List<Product> productsList = new ArrayList<>();

        this.transactionService.executeInTransaction(em -> {

            for (Long id_product : idProducts) {
                Product product = em.find(Product.class, id_product);

                if (product == null) {
                    throw new RuntimeException("Producto no encontrado: " + id_product);
                }

                productsList.add(product);
            }
        });

        return productsList;
    }

    private ShoppingCart crearCarritoConProductos(List<Product> productList) {

        ShoppingCart cart = new ShoppingCart();
        
        for (Product product : productList) {
            cart.addProduct(product);
        }
        return cart;
    }

}
