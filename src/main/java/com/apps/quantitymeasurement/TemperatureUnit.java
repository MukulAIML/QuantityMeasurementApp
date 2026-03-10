package com.apps.quantitymeasurement;

import java.util.function.Function;

public enum TemperatureUnit implements IMeasurable {

    CELSIUS {
        final Function<Double, Double> toCelsius = celsius -> celsius;
        final Function<Double, Double> fromCelsius = celsius -> celsius;

        @Override
        public double convertToBaseUnit(double value) {
            return toCelsius.apply(value);
        }

        @Override
        public double convertFromBaseUnit(double baseValue) {
            return fromCelsius.apply(baseValue);
        }
    },

    FAHRENHEIT {
        final Function<Double, Double> toCelsius = fahrenheit -> (fahrenheit - 32) * 5.0 / 9.0;
        final Function<Double, Double> fromCelsius = celsius -> celsius * 9.0 / 5.0 + 32;

        @Override
        public double convertToBaseUnit(double value) {
            return toCelsius.apply(value);
        }

        @Override
        public double convertFromBaseUnit(double baseValue) {
            return fromCelsius.apply(baseValue);
        }
    };

    // Lambda: temperature does not support arithmetic
    SupportsArithmetic supportsArithmetic = () -> false;

    @Override
    public double getConversionFactor() {
        return 1.0;
    }

    @Override
    public String getUnitName() {
        return this.name();
    }

    @Override
    public boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();
    }

    @Override
    public void validateOperationSupport(String operation) {
        throw new UnsupportedOperationException(
            "Temperature does not support " + operation +
            ". Temperature arithmetic is not meaningful for absolute values."
        );
    }
}