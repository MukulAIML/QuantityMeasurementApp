package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class QuantityMeasurementAppTest {

    @Test
    public void feetToInches() {
        Length feet = new Length(1.0, LengthUnit.FEET);
        Length result = feet.convertTo(LengthUnit.INCHES);
        assertEquals("12.0 INCHES", result.toString());
    }

    @Test
    public void inchesToFeet() {
        Length inches = new Length(24.0, LengthUnit.INCHES);
        Length result = inches.convertTo(LengthUnit.FEET);
        assertEquals("2.0 FEET", result.toString());
    }

    @Test
    public void yardsToInches() {
        Length yards = new Length(1.0, LengthUnit.YARDS);
        Length result = yards.convertTo(LengthUnit.INCHES);
        assertEquals("36.0 INCHES", result.toString());
    }

    @Test
    public void inchesToYards() {
        Length inches = new Length(72.0, LengthUnit.INCHES);
        Length result = inches.convertTo(LengthUnit.YARDS);
        assertEquals("2.0 YARDS", result.toString());
    }

    @Test
    public void feetToYards() {
        Length feet = new Length(6.0, LengthUnit.FEET);
        Length result = feet.convertTo(LengthUnit.YARDS);
        assertEquals("2.0 YARDS", result.toString());
    }

    @Test
    public void zeroConversion() {
        Length zero = new Length(0.0, LengthUnit.FEET);
        Length result = zero.convertTo(LengthUnit.INCHES);
        assertEquals("0.0 INCHES", result.toString());
    }

    @Test
    public void negativeConversion() {
        Length negative = new Length(-1.0, LengthUnit.FEET);
        Length result = negative.convertTo(LengthUnit.INCHES);
        assertEquals("-12.0 INCHES", result.toString());
    }

    @Test
    public void sameUnitConversion() {
        Length feet = new Length(5.0, LengthUnit.FEET);
        Length result = feet.convertTo(LengthUnit.FEET);
        assertEquals("5.0 FEET", result.toString());
    }

    @Test
    public void roundTripConversion() {
        Length original = new Length(3.0, LengthUnit.YARDS);

        Length inches = original.convertTo(LengthUnit.INCHES);
        Length back = inches.convertTo(LengthUnit.YARDS);

        assertTrue(original.equals(back));
    }

    @Test
    public void nullTargetUnitThrows() {
        Length feet = new Length(1.0, LengthUnit.FEET);

        assertThrows(IllegalArgumentException.class, () -> {
            feet.convertTo(null);
        });
    }
    
    @Test
    public void testAddition_SameUnit_FeetPlusFeet() {
        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(2.0, LengthUnit.FEET);
        Length result = l1.add(l2);
        assertEquals("3.0 FEET", result.toString());
    }

    @Test
    public void testAddition_SameUnit_InchPlusInch() {
        Length l1 = new Length(6.0, LengthUnit.INCHES);
        Length l2 = new Length(6.0, LengthUnit.INCHES);
        Length result = l1.add(l2);
        assertEquals("12.0 INCHES", result.toString());
    }
    
    @Test
    public void testAddition_ExplicitTargetUnit_Feet() {
        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(12.0, LengthUnit.INCHES);
        Length result = l1.add(l2, LengthUnit.FEET);
        assertEquals("2.0 FEET", result.toString());
    }

    @Test
    public void testAddition_ExplicitTargetUnit_Inches() {
        Length l1 = new Length(1.0, LengthUnit.FEET);
        Length l2 = new Length(12.0, LengthUnit.INCHES);
        Length result = l1.add(l2, LengthUnit.INCHES);
        assertEquals("24.0 INCHES", result.toString());
    }
}
