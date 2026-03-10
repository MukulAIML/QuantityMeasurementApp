package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    @Test
    public void feetToInches() {
        Quantity<LengthUnit> feet = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> result = feet.convertTo(LengthUnit.INCHES);
        assertEquals("12.0 INCHES", result.toString());
    }

    @Test
    public void inchesToFeet() {
        Quantity<LengthUnit> inches = new Quantity<>(24.0, LengthUnit.INCHES);
        Quantity<LengthUnit> result = inches.convertTo(LengthUnit.FEET);
        assertEquals("2.0 FEET", result.toString());
    }

    @Test
    public void yardsToInches() {
        Quantity<LengthUnit> yards = new Quantity<>(1.0, LengthUnit.YARDS);
        Quantity<LengthUnit> result = yards.convertTo(LengthUnit.INCHES);
        assertEquals("36.0 INCHES", result.toString());
    }

    @Test
    public void inchesToYards() {
        Quantity<LengthUnit> inches = new Quantity<>(72.0, LengthUnit.INCHES);
        Quantity<LengthUnit> result = inches.convertTo(LengthUnit.YARDS);
        assertEquals("2.0 YARDS", result.toString());
    }

    @Test
    public void feetToYards() {
        Quantity<LengthUnit> feet = new Quantity<>(6.0, LengthUnit.FEET);
        Quantity<LengthUnit> result = feet.convertTo(LengthUnit.YARDS);
        assertEquals("2.0 YARDS", result.toString());
    }

    @Test
    public void zeroConversion() {
        Quantity<LengthUnit> zero = new Quantity<>(0.0, LengthUnit.FEET);
        Quantity<LengthUnit> result = zero.convertTo(LengthUnit.INCHES);
        assertEquals("0.0 INCHES", result.toString());
    }

    @Test
    public void negativeConversion() {
        Quantity<LengthUnit> negative = new Quantity<>(-1.0, LengthUnit.FEET);
        Quantity<LengthUnit> result = negative.convertTo(LengthUnit.INCHES);
        assertEquals("-12.0 INCHES", result.toString());
    }

    @Test
    public void sameUnitConversion() {
        Quantity<LengthUnit> feet = new Quantity<>(5.0, LengthUnit.FEET);
        Quantity<LengthUnit> result = feet.convertTo(LengthUnit.FEET);
        assertEquals("5.0 FEET", result.toString());
    }

    @Test
    public void roundTripConversion() {
        Quantity<LengthUnit> original = new Quantity<>(3.0, LengthUnit.YARDS);
        Quantity<LengthUnit> inches = original.convertTo(LengthUnit.INCHES);
        Quantity<LengthUnit> back = inches.convertTo(LengthUnit.YARDS);
        assertTrue(original.equals(back));
    }

    @Test
    public void nullTargetUnitThrows() {
        Quantity<LengthUnit> feet = new Quantity<>(1.0, LengthUnit.FEET);
        assertThrows(IllegalArgumentException.class, () -> {
            feet.convertTo(null);
        });
    }

    @Test
    public void testAddition_SameUnit_FeetPlusFeet() {
        Quantity<LengthUnit> l1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> l2 = new Quantity<>(2.0, LengthUnit.FEET);
        Quantity<LengthUnit> result = l1.add(l2);
        assertEquals("3.0 FEET", result.toString());
    }

    @Test
    public void testAddition_SameUnit_InchPlusInch() {
        Quantity<LengthUnit> l1 = new Quantity<>(6.0, LengthUnit.INCHES);
        Quantity<LengthUnit> l2 = new Quantity<>(6.0, LengthUnit.INCHES);
        Quantity<LengthUnit> result = l1.add(l2);
        assertEquals("12.0 INCHES", result.toString());
    }

    @Test
    public void testAddition_ExplicitTargetUnit_Feet() {
        Quantity<LengthUnit> l1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> l2 = new Quantity<>(12.0, LengthUnit.INCHES);
        Quantity<LengthUnit> result = l1.add(l2, LengthUnit.FEET);
        assertEquals("2.0 FEET", result.toString());
    }

    @Test
    public void testAddition_ExplicitTargetUnit_Inches() {
        Quantity<LengthUnit> l1 = new Quantity<>(1.0, LengthUnit.FEET);
        Quantity<LengthUnit> l2 = new Quantity<>(12.0, LengthUnit.INCHES);
        Quantity<LengthUnit> result = l1.add(l2, LengthUnit.INCHES);
        assertEquals("24.0 INCHES", result.toString());
    }

    @Test
    public void testEquality_KilogramToKilogram_SameValue() {
        Quantity<WeightUnit> w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        assertTrue(w1.equals(w2));
    }

    @Test
    public void testConversion_KilogramToGram() {
        Quantity<WeightUnit> result = new Quantity<>(1.0, WeightUnit.KILOGRAM)
                .convertTo(WeightUnit.GRAM);
        assertEquals("1000.0 GRAM", result.toString());
    }

    @Test
    public void testAddition_SameUnit_KilogramPlusKilogram() {
        Quantity<WeightUnit> w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(2.0, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> result = w1.add(w2);
        assertEquals("3.0 KILOGRAM", result.toString());
    }
}