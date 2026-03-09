package com.EqualityMearsurement;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class FeetMeasurementEqualityTest {
	
	@Test
    void testFeetEquality_SameValue() {
		FeetMeasurementEqualty.Feet feet1 = new FeetMeasurementEqualty.Feet(5.0);
		FeetMeasurementEqualty.Feet feet2 = new FeetMeasurementEqualty.Feet(5.0);

        assertTrue(feet1.equals(feet2));
    }
	
	@Test
    void testFeetEquality_DifferentValue() {
        FeetMeasurementEqualty.Feet feet1 = new FeetMeasurementEqualty.Feet(5.0);
        FeetMeasurementEqualty.Feet feet2 = new FeetMeasurementEqualty.Feet(6.0);

        assertFalse(feet1.equals(feet2));
    }
	
	@Test
    void testFeetEquality_SameReference() {
        FeetMeasurementEqualty.Feet feet1 = new FeetMeasurementEqualty.Feet(5.0);

        assertTrue(feet1.equals(feet1));
    }
	
	@Test
    void testFeetEquality_NullComparison() {
        FeetMeasurementEqualty.Feet feet1 = new FeetMeasurementEqualty.Feet(5.0);

        assertFalse(feet1.equals(null));
    }
	
	void testFeetEquality_DifferentObjectType() {
        FeetMeasurementEqualty.Feet feet1 = new FeetMeasurementEqualty.Feet(5.0);

        assertFalse(feet1.equals("5.0"));
    }
}
