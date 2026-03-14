package com.apps.quantitymeasurement.entity;

public class QuantityDTO {

	public interface IMeasurableUnit {
		String getUnitName();

		String getMeasurementType();
	}

	public enum LengthUnit implements IMeasurableUnit {
		FEET, INCHES, YARDS, CENTIMETERS;

		@Override
		public String getUnitName() {
			return this.name();
		}

		@Override
		public String getMeasurementType() {
			return "LENGTH";
		}
	}

	public enum WeightUnit implements IMeasurableUnit {
		KILOGRAM, GRAM, POUND;

		@Override
		public String getUnitName() {
			return this.name();
		}

		@Override
		public String getMeasurementType() {
			return "WEIGHT";
		}
	}

	public enum VolumeUnit implements IMeasurableUnit {
		LITRE, MILLILITRE, GALLON;

		@Override
		public String getUnitName() {
			return this.name();
		}

		@Override
		public String getMeasurementType() {
			return "VOLUME";
		}
	}

	public enum TemperatureUnit implements IMeasurableUnit {
		CELSIUS, FAHRENHEIT;

		@Override
		public String getUnitName() {
			return this.name();
		}

		@Override
		public String getMeasurementType() {
			return "TEMPERATURE";
		}
	}

	private final double value;
	private final IMeasurableUnit unit;

	public QuantityDTO(double value, IMeasurableUnit unit) {
		this.value = value;
		this.unit = unit;
	}

	public double getValue() {
		return value;
	}

	public IMeasurableUnit getUnit() {
		return unit;
	}

	public String getUnitName() {
		return unit.getUnitName();
	}

	public String getMeasurementType() {
		return unit.getMeasurementType();
	}

	@Override
	public String toString() {
		return value + " " + unit.getUnitName() + " (" + unit.getMeasurementType() + ")";
	}
}