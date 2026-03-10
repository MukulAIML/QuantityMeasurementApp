package com.apps.quantitymeasurement;

public class Weight {
    private final double value;
    private final WeightUnit unit;

    public Weight(double value, WeightUnit unit) {
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

    public WeightUnit getUnit() {
        return unit;
    }

    private double convertToBaseUnit() {
        return unit.convertToBaseUnit(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weight that = (Weight) o;
        return Double.compare(this.convertToBaseUnit(), that.convertToBaseUnit()) == 0;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(convertToBaseUnit());
    }

    public Weight convertTo(WeightUnit targetUnit) {
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null.");
        }
        double baseValue = unit.convertToBaseUnit(value);
        double targetValue = targetUnit.convertFromBaseUnit(baseValue);
        targetValue = Math.round(targetValue * 100.0) / 100.0;
        return new Weight(targetValue, targetUnit);
    }

    private Weight addInTargetUnit(Weight thatWeight, WeightUnit targetUnit) {
        if (thatWeight == null) {
            throw new IllegalArgumentException("Weight to add cannot be null.");
        }
        if (targetUnit == null) {
            throw new IllegalArgumentException("Target unit cannot be null.");
        }
        double sumInBase = this.unit.convertToBaseUnit(this.value)
                         + thatWeight.unit.convertToBaseUnit(thatWeight.value);
        double resultValue = targetUnit.convertFromBaseUnit(sumInBase);
        resultValue = Math.round(resultValue * 100.0) / 100.0;
        return new Weight(resultValue, targetUnit);
    }

    public Weight add(Weight thatWeight) {
        return addInTargetUnit(thatWeight, this.unit);
    }

    public Weight add(Weight thatWeight, WeightUnit targetUnit) {
        return addInTargetUnit(thatWeight, targetUnit);
    }

    @Override
    public String toString() {
        return value + " " + unit.toString();
    }
}