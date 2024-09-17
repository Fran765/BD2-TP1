package ar.unrn.tp.jpa.services;

import ar.unrn.tp.api.DiscountService;
import ar.unrn.tp.api.TransactionService;
import ar.unrn.tp.domain.model.Brand;
import ar.unrn.tp.domain.model.BuyDiscount;
import ar.unrn.tp.domain.model.CardType;
import ar.unrn.tp.domain.model.ProductDiscount;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;


@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final TransactionService transactionService;

    @Override
    public void crearDescuentoSobreTotal(String marcaTarjeta, LocalDate fechaDesde, LocalDate fechaHasta, float porcentaje) {
        crearDescuentoInterno(marcaTarjeta, fechaDesde, fechaHasta, porcentaje, true);
    }

    @Override
    public void crearDescuento(String marcaProducto, LocalDate fechaDesde, LocalDate fechaHasta, float porcentaje) {
        crearDescuentoInterno(marcaProducto, fechaDesde, fechaHasta, porcentaje, false);
    }

    private void crearDescuentoInterno(String marca, LocalDate fechaDesde, LocalDate fechaHasta, float porcentaje, boolean esTarjeta) {
        this.transactionService.executeInTransaction(em -> {
            if (esTarjeta) {
                BuyDiscount totalDiscount = new BuyDiscount(porcentaje,
                        fechaDesde,
                        fechaHasta,
                        CardType.valueOf(marca.toUpperCase())
                );
                em.persist(totalDiscount);
            } else {
                ProductDiscount productDiscount = new ProductDiscount(porcentaje,
                        fechaDesde,
                        fechaHasta,
                        Brand.valueOf(marca.toUpperCase())
                );
                em.persist(productDiscount);
            }
        });
    }
}