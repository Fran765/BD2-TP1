package org.example.model;

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
            throw new RuntimeException("las fechas del descuento no pueden er iguales");

        if (startDate.isAfter(endDate))
            throw new RuntimeException("La fecha de inicio no puede ser mayor que la ficha de fin.");

    }

    public abstract double applyDiscount(double price);

    public boolean isOnDate() {
        LocalDate now = LocalDate.now();
        return (now.isEqual(startDate) || now.isAfter(startDate)) && now.isBefore(endDate);
    }
}
