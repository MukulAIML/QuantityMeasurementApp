package com.apps.quantitymeasurement.entity;

import com.apps.quantitymeasurement.unit.IMeasurable;
import com.apps.quantitymeasurement.Quantity;

public class QuantityModel<U extends IMeasurable> {

    private final double value;
    private final U unit;

    public QuantityModel(double value, U unit) {
        this.value = value;
        this.unit = unit;
    }

    public double getValue() { return value; }
    public U getUnit() { return unit; }

    public Quantity<U> toQuantity() {
        return new Quantity<>(value, unit);
    }

    @Override
    public String toString() {
        return value + " " + unit.getUnitName();
    }
}