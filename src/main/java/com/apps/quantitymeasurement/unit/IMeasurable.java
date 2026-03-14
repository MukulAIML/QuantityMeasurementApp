package com.apps.quantitymeasurement.unit;

@FunctionalInterface
interface SupportsArithmetic {
    boolean isSupported();
}

public interface IMeasurable {
    double getConversionFactor();
    double convertToBaseUnit(double value);
    double convertFromBaseUnit(double baseValue);
    String getUnitName();
    String getMeasurementType();

    static IMeasurable getUnitByName(String measurementType, String unitName) {
        switch (measurementType.toUpperCase()) {
            case "LENGTH":      return LengthUnit.valueOf(unitName.toUpperCase());
            case "WEIGHT":      return WeightUnit.valueOf(unitName.toUpperCase());
            case "VOLUME":      return VolumeUnit.valueOf(unitName.toUpperCase());
            case "TEMPERATURE": return TemperatureUnit.valueOf(unitName.toUpperCase());
            default:
                throw new IllegalArgumentException(
                    "Unknown measurement type: " + measurementType);
        }
    }

    SupportsArithmetic supportsArithmetic = () -> true;

    default boolean supportsArithmetic() {
        return supportsArithmetic.isSupported();
    }

    default void validateOperationSupport(String operation) {}
}