package com.apps.quantitymeasurement;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.apps.quantitymeasurement.controller.QuantityMeasurementController;
import com.apps.quantitymeasurement.entity.QuantityDTO;
import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.apps.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.apps.quantitymeasurement.service.IQuantityMeasurementService;
import com.apps.quantitymeasurement.service.QuantityMeasurementServiceImpl;

import com.apps.quantitymeasurement.unit.LengthUnit;
import com.apps.quantitymeasurement.unit.WeightUnit;
import com.apps.quantitymeasurement.unit.VolumeUnit;
import com.apps.quantitymeasurement.unit.TemperatureUnit;

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
		Quantity<WeightUnit> result = new Quantity<>(1.0, WeightUnit.KILOGRAM).convertTo(WeightUnit.GRAM);
		assertEquals("1000.0 GRAM", result.toString());
	}

	@Test
	public void testAddition_SameUnit_KilogramPlusKilogram() {
		Quantity<WeightUnit> w1 = new Quantity<>(1.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> w2 = new Quantity<>(2.0, WeightUnit.KILOGRAM);
		Quantity<WeightUnit> result = w1.add(w2);
		assertEquals("3.0 KILOGRAM", result.toString());
	}

	@Test
	public void testVolumeUnitEnum_LitreConstant() {
		assertEquals(1.0, VolumeUnit.LITRE.getConversionFactor());
	}

	@Test
	public void testConversion_LitreToMillilitre() {
		Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE).convertTo(VolumeUnit.MILLILITRE);
		assertEquals("1000.0 MILLILITRE", result.toString());
	}

	@Test
	public void testConvertFromBaseUnit_LitreToMillilitre() {
		assertEquals(1000.0, VolumeUnit.MILLILITRE.convertFromBaseUnit(1.0));
	}

	@Test
	public void testConversion_RoundTrip_Volume() {
		Quantity<VolumeUnit> original = new Quantity<>(1.5, VolumeUnit.LITRE);
		Quantity<VolumeUnit> roundTrip = original.convertTo(VolumeUnit.MILLILITRE).convertTo(VolumeUnit.LITRE);
		assertTrue(original.equals(roundTrip));
	}

	@Test
	public void testEquality_VolumeVsLength_Incompatible() {
		Quantity<VolumeUnit> volume = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<LengthUnit> length = new Quantity<>(1.0, LengthUnit.FEET);
		assertFalse(volume.equals(length));
	}

	@Test
	public void testAddition_CrossUnit_LitrePlusMillilitre() {
		Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE)
				.add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE));
		assertEquals("2.0 LITRE", result.toString());
	}

	@Test
	public void testEquality_LitreToMillilitre_EquivalentValue() {
		Quantity<VolumeUnit> q1 = new Quantity<>(1.0, VolumeUnit.LITRE);
		Quantity<VolumeUnit> q2 = new Quantity<>(1000.0, VolumeUnit.MILLILITRE);
		assertTrue(q1.equals(q2));
	}

	@Test
	public void testAddition_ExplicitTargetUnit_Millilitre() {
		Quantity<VolumeUnit> result = new Quantity<>(1.0, VolumeUnit.LITRE)
				.add(new Quantity<>(1000.0, VolumeUnit.MILLILITRE), VolumeUnit.MILLILITRE);
		assertEquals("2000.0 MILLILITRE", result.toString());
	}

	@Test
	public void testSubtraction_SameUnit_FeetMinusFeet() {
		Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
				.subtract(new Quantity<>(5.0, LengthUnit.FEET));
		assertEquals("5.0 FEET", result.toString());
	}

	@Test
	public void testSubtraction_CrossUnit_FeetMinusInches() {
		Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
				.subtract(new Quantity<>(6.0, LengthUnit.INCHES));
		assertEquals("9.5 FEET", result.toString());
	}

	@Test
	public void testSubtraction_ChainedOperations() {
		Quantity<LengthUnit> result = new Quantity<>(10.0, LengthUnit.FEET)
				.subtract(new Quantity<>(2.0, LengthUnit.FEET)).subtract(new Quantity<>(1.0, LengthUnit.FEET));
		assertEquals("7.0 FEET", result.toString());
	}

	@Test
	public void testDivision_SameUnit_FeetDividedByFeet() {
		double result = new Quantity<>(10.0, LengthUnit.FEET).divide(new Quantity<>(2.0, LengthUnit.FEET));
		assertEquals(5.0, result);
	}

	@Test
	public void testDivision_CrossUnit_InchesAndFeet() {
		double result = new Quantity<>(24.0, LengthUnit.INCHES).divide(new Quantity<>(2.0, LengthUnit.FEET));
		assertEquals(1.0, result);
	}

	@Test
	public void testDivision_RatioLessThanOne() {
		double result = new Quantity<>(5.0, LengthUnit.FEET).divide(new Quantity<>(10.0, LengthUnit.FEET));
		assertEquals(0.5, result);
	}

	@Test
	public void testDivision_CrossCategory() {
		Quantity<LengthUnit> length = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<WeightUnit> weight = new Quantity<>(5.0, WeightUnit.KILOGRAM);
		assertThrows(IllegalArgumentException.class, () -> length.divide((Quantity) weight));
	}

	@Test
	public void testSubtractionAndDivision_Integration() {
		Quantity<LengthUnit> a = new Quantity<>(10.0, LengthUnit.FEET);
		Quantity<LengthUnit> b = new Quantity<>(5.0, LengthUnit.FEET);
		Quantity<LengthUnit> c = new Quantity<>(2.5, LengthUnit.FEET);
		double result = a.subtract(b).divide(c);
		assertEquals(2.0, result);
	}

	@Test
	public void testValidation_NullOperand_Add() {
		Quantity<LengthUnit> q = new Quantity<>(10.0, LengthUnit.FEET);
		assertThrows(IllegalArgumentException.class, () -> q.add(null));
	}

	@Test
	public void testValidation_NullOperand_Subtract() {
		Quantity<LengthUnit> q = new Quantity<>(10.0, LengthUnit.FEET);
		assertThrows(IllegalArgumentException.class, () -> q.subtract(null));
	}

	// Temperature Equality Tests

	@Test
	public void testTemperatureEquality_CelsiusToCelsius_SameValue() {
		Quantity<TemperatureUnit> q1 = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
		Quantity<TemperatureUnit> q2 = new Quantity<>(0.0, TemperatureUnit.CELSIUS);
		assertTrue(q1.equals(q2));
	}

	// Temperature Conversion Tests

	@Test
	public void testTemperatureConversion_CelsiusToFahrenheit_0() {
		Quantity<TemperatureUnit> result = new Quantity<>(0.0, TemperatureUnit.CELSIUS)
				.convertTo(TemperatureUnit.FAHRENHEIT);
		assertEquals("32.0 FAHRENHEIT", result.toString());
	}

	// Unsupported Operation Tests

	@Test
	public void testTemperatureUnsupportedOperation_Add() {
		Quantity<TemperatureUnit> q = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
		assertThrows(UnsupportedOperationException.class, () -> q.add(new Quantity<>(50.0, TemperatureUnit.CELSIUS)));
	}

	@Test
	public void testTemperatureUnsupportedOperation_Subtract() {
		Quantity<TemperatureUnit> q = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
		assertThrows(UnsupportedOperationException.class,
				() -> q.subtract(new Quantity<>(50.0, TemperatureUnit.CELSIUS)));
	}

	// Cross-Category Prevention Tests

	@Test
	public void testTemperatureVsLength_Incompatible() {
		Quantity<TemperatureUnit> temp = new Quantity<>(100.0, TemperatureUnit.CELSIUS);
		Quantity<LengthUnit> length = new Quantity<>(100.0, LengthUnit.FEET);
		assertFalse(temp.equals(length));
	}

	// UC 15 test cases

	@Test
	public void testEntity_SingleOperandConstruction() {
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity("1.0 FEET (LENGTH)", "CONVERT",
				"12.0 INCHES (LENGTH)");
		assertFalse(entity.hasError());
		assertEquals("CONVERT", entity.getOperation());
		assertEquals("12.0 INCHES (LENGTH)", entity.getResult());
	}

	@Test
	public void testEntity_BinaryOperandConstruction() {
		QuantityMeasurementEntity entity = new QuantityMeasurementEntity("1.0 FEET (LENGTH)", "12.0 INCHES (LENGTH)",
				"ADD", "2.0 FEET (LENGTH)");
		assertFalse(entity.hasError());
		assertEquals("ADD", entity.getOperation());
		assertNotNull(entity.getResult());
	}

	@Test
	public void testService_Compare_SameUnit_Success() {
		IQuantityMeasurementService service = new QuantityMeasurementServiceImpl(
				QuantityMeasurementCacheRepository.getInstance());
		boolean result = service.compare(new QuantityDTO(1.0, QuantityDTO.LengthUnit.FEET),
				new QuantityDTO(12.0, QuantityDTO.LengthUnit.INCHES));
		assertTrue(result);
	}

	@Test
	public void testController_PerformComparison_Success() {
		QuantityMeasurementController controller = new QuantityMeasurementController(
				new QuantityMeasurementServiceImpl(QuantityMeasurementCacheRepository.getInstance()));
		boolean result = controller.performComparison(new QuantityDTO(1.0, QuantityDTO.WeightUnit.KILOGRAM),
				new QuantityDTO(1000.0, QuantityDTO.WeightUnit.GRAM));
		assertTrue(result);
	}

	@Test
	public void testController_PerformConversion_Success() {
		QuantityMeasurementController controller = new QuantityMeasurementController(
				new QuantityMeasurementServiceImpl(QuantityMeasurementCacheRepository.getInstance()));
		QuantityDTO result = controller.performConversion(new QuantityDTO(1.0, QuantityDTO.VolumeUnit.LITRE),
				"MILLILITRE");
		assertNotNull(result);
		assertEquals(1000.0, result.getValue());
	}

}