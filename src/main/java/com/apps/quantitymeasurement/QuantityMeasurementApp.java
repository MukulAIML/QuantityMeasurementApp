package com.apps.quantitymeasurement;

public class QuantityMeasurementApp {

    public static <U extends IMeasurable> boolean demonstrateEquality(
            Quantity<U> q1, Quantity<U> q2) {
        boolean result = q1.equals(q2);
        System.out.println(q1 + " == " + q2 + " ? " + result);
        return result;
    }

    public static <U extends IMeasurable> Quantity<U> demonstrateConversion(
            Quantity<U> quantity, U targetUnit) {
        Quantity<U> result = quantity.convertTo(targetUnit);
        System.out.println(quantity + " -> " + result);
        return result;
    }

    public static <U extends IMeasurable> Quantity<U> demonstrateAddition(
            Quantity<U> q1, Quantity<U> q2) {
        Quantity<U> result = q1.add(q2);
        System.out.println("add(" + q1 + ", " + q2 + ") = " + result);
        return result;
    }

    public static <U extends IMeasurable> Quantity<U> demonstrateAddition(
            Quantity<U> q1, Quantity<U> q2, U targetUnit) {
        Quantity<U> result = q1.add(q2, targetUnit);
        System.out.println("add(" + q1 + ", " + q2 + ", " + targetUnit + ") = " + result);
        return result;
    }

    public static void main(String[] args) {

        System.out.println("=== Length Operations ===");
        demonstrateEquality(
            new Quantity<>(1.0, LengthUnit.FEET),
            new Quantity<>(12.0, LengthUnit.INCHES)
        );
        demonstrateEquality(
            new Quantity<>(1.0, LengthUnit.YARDS),
            new Quantity<>(36.0, LengthUnit.INCHES)
        );
        demonstrateConversion(
            new Quantity<>(1.0, LengthUnit.FEET), LengthUnit.INCHES
        );
        demonstrateConversion(
            new Quantity<>(30.48, LengthUnit.CENTIMETERS), LengthUnit.FEET
        );
        demonstrateAddition(
            new Quantity<>(1.0, LengthUnit.FEET),
            new Quantity<>(12.0, LengthUnit.INCHES),
            LengthUnit.FEET
        );
        demonstrateAddition(
            new Quantity<>(1.0, LengthUnit.FEET),
            new Quantity<>(12.0, LengthUnit.INCHES),
            LengthUnit.YARDS
        );

        System.out.println("\n=== Weight Operations ===");
        demonstrateEquality(
            new Quantity<>(1.0, WeightUnit.KILOGRAM),
            new Quantity<>(1000.0, WeightUnit.GRAM)
        );
        demonstrateEquality(
            new Quantity<>(500.0, WeightUnit.GRAM),
            new Quantity<>(0.5, WeightUnit.KILOGRAM)
        );
        demonstrateConversion(
            new Quantity<>(1.0, WeightUnit.KILOGRAM), WeightUnit.GRAM
        );
        demonstrateConversion(
            new Quantity<>(2.0, WeightUnit.POUND), WeightUnit.KILOGRAM
        );
        demonstrateAddition(
            new Quantity<>(1.0, WeightUnit.KILOGRAM),
            new Quantity<>(1000.0, WeightUnit.GRAM),
            WeightUnit.KILOGRAM
        );
        demonstrateAddition(
            new Quantity<>(1.0, WeightUnit.KILOGRAM),
            new Quantity<>(1000.0, WeightUnit.GRAM),
            WeightUnit.GRAM
        );

        Quantity<LengthUnit> length = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<WeightUnit> weight = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        System.out.println("Quantity(1.0, FEET).equals(Quantity(1.0, KILOGRAM)) ? "
            + length.equals(weight));
    }
}