package ar.unrn.tp.domain.model;

import java.time.LocalDate;

public abstract class Discount {
    protected double percent;
    protected LocalDate startDate;
    protected LocalDate endDate;

    public Discount(double percent, LocalDate startDate, LocalDate endDate) {
        this.percent = percent;
        this.validateDates(startDate, endDate);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    private void validateDates(LocalDate startDate, LocalDate endDate) {
        if (startDate.equals(endDate))
            throw new RuntimeException("Las fechas del descuento no pueden ser iguales.");

        if (startDate.isAfter(endDate))
            throw new RuntimeException("La fecha de inicio no puede ser mayor que la ficha de fin.");

    }

    public double applyDiscount(double price) {
        return price * (1 - this.percent / 100);
    }

    protected boolean isOnDate() {
        LocalDate today = LocalDate.now();
        return !today.isBefore(startDate) && !today.isAfter(endDate);
    }
}
