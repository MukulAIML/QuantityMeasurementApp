package com.apps.quantitymeasurement;

public class Quantity<U extends IMeasurable> {
    private final double value;
    private final U unit;

    public Quantity(double value, U unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null.");
        }
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Value must be a finite number.");
        }
        this.value = value;
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public U getUnit() {
        return unit;
    }

    private double convertToBaseUnit() {
        return unit.convertToBaseUnit(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quantity<?> that = (Quantity<?>) o;
        if (this.unit.getClass() != that.unit.getClass()) return false;
        return Double.compare(this.convertToBaseUnit(), that.convertToBaseUnit()) == 0;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(convertToBaseUnit());
    }

    public Quantity<U> convertTo(U targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null.");
        }
        double baseValue = unit.convertToBaseUnit(value);
        double targetValue = targetUnit.convertFromBaseUnit(baseValue);
        targetValue = Math.round(targetValue * 100.0) / 100.0;
        return new Quantity<>(targetValue, targetUnit);
    }

    private Quantity<U> addInTargetUnit(Quantity<U> other, U targetUnit) {
        if (other == null) {
            throw new IllegalArgumentException("Quantity to add cannot be null.");
        }
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null.");
        }
        double sumInBase = this.unit.convertToBaseUnit(this.value)
                         + other.unit.convertToBaseUnit(other.value);
        double resultValue = targetUnit.convertFromBaseUnit(sumInBase);
        resultValue = Math.round(resultValue * 100.0) / 100.0;
        return new Quantity<>(resultValue, targetUnit);
    }

    public Quantity<U> add(Quantity<U> other) {
        return addInTargetUnit(other, this.unit);
    }

    public Quantity<U> add(Quantity<U> other, U targetUnit) {
        return addInTargetUnit(other, targetUnit);
    }
    
    private Quantity<U> subtractInTargetUnit(Quantity<U> other, U targetUnit) {
        if (other == null) {
            throw new IllegalArgumentException("Quantity to subtract cannot be null.");
        }
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null.");
        }
        if (this.unit.getClass() != other.unit.getClass()) {
            throw new IllegalArgumentException("Cannot subtract different measurement categories.");
        }
        double differenceInBase = this.unit.convertToBaseUnit(this.value)
                                - other.unit.convertToBaseUnit(other.value);
        double resultValue = targetUnit.convertFromBaseUnit(differenceInBase);
        resultValue = Math.round(resultValue * 100.0) / 100.0;
        return new Quantity<>(resultValue, targetUnit);
    }

    public Quantity<U> subtract(Quantity<U> other) {
        return subtractInTargetUnit(other, this.unit);
    }

    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
        return subtractInTargetUnit(other, targetUnit);
    }

    public double divide(Quantity<U> other) {
        if (other == null) {
            throw new IllegalArgumentException("Quantity to divide cannot be null.");
        }
        if (this.unit.getClass() != other.unit.getClass()) {
            throw new IllegalArgumentException("Cannot divide different measurement categories.");
        }
        double otherInBase = other.unit.convertToBaseUnit(other.value);
        if (otherInBase == 0.0) {
            throw new ArithmeticException("Division by zero.");
        }
        return this.unit.convertToBaseUnit(this.value) / otherInBase;
    }

    @Override
    public String toString() {
        return value + " " + unit.getUnitName();
    }
}