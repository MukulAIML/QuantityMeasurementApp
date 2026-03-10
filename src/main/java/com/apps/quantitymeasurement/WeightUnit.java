package com.apps.quantitymeasurement;

public enum WeightUnit {
	MILLIGRAM(0.001),
	GRAM(0.001),
    KILOGRAM(1.0),
    POUND(0.453592),
    TONNE(1_000_000.0);

    private final double conversionFactor;

    WeightUnit(double conversionFactor) {
        this.conversionFactor = conversionFactor;
    }

    public double getConversionFactor() {
        return conversionFactor;
    }

    public double convertToBaseUnit(double value) {
        return value * conversionFactor;
    }

    public double convertFromBaseUnit(double baseValue) {
        return baseValue / conversionFactor;
    }
}