package com.apps.quantitymeasurement;

public class Length {
    private double value;
    private LengthUnit unit;

    public Length(double value, LengthUnit unit) {
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

    public LengthUnit getUnit() {
        return unit;
    }

    private double convertToBaseUnit() {
        return unit.convertToBaseUnit(value);
    }

    public boolean compare(Length thatLength) {
        return thatLength.convertToBaseUnit() == this.convertToBaseUnit();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return compare((Length) o);
    }

    @Override
    public int hashCode() {
        return Double.hashCode(convertToBaseUnit());
    }

    public Length convertTo(LengthUnit targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null.");
        }
        double baseValue = unit.convertToBaseUnit(value);
        double targetValue = targetUnit.convertFromBaseUnit(baseValue);
        targetValue = Math.round(targetValue * 100.0) / 100.0;
        return new Length(targetValue, targetUnit);
    }

    private Length addInTargetUnit(Length thatLength, LengthUnit targetUnit) {
        if (thatLength == null) {
            throw new IllegalArgumentException("Length to add cannot be null.");
        }
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null.");
        }
        double sumInBase = this.unit.convertToBaseUnit(this.value)
                         + thatLength.unit.convertToBaseUnit(thatLength.value);
        double resultValue = targetUnit.convertFromBaseUnit(sumInBase);
        resultValue = Math.round(resultValue * 100.0) / 100.0;
        return new Length(resultValue, targetUnit);
    }

    public Length add(Length thatLength) {
        return addInTargetUnit(thatLength, this.unit);
    }

    public Length add(Length thatLength, LengthUnit targetUnit) {
        return addInTargetUnit(thatLength, targetUnit);
    }

    @Override
    public String toString() {
        return value + " " + unit.toString();
    }
}