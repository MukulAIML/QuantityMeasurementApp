package com.apps.quantitymeasurement.unit;

import java.util.function.Function;

public enum TemperatureUnit implements IMeasurable {

	CELSIUS {
		private final Function<Double, Double> toCelsius = v -> v;
		private final Function<Double, Double> fromCelsius = v -> v;

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
		private final Function<Double, Double> toCelsius = f -> (f - 32) * 5.0 / 9.0;
		private final Function<Double, Double> fromCelsius = c -> c * 9.0 / 5.0 + 32;

		@Override
		public double convertToBaseUnit(double value) {
			return toCelsius.apply(value);
		}

		@Override
		public double convertFromBaseUnit(double baseValue) {
			return fromCelsius.apply(baseValue);
		}
	};

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
	public String getMeasurementType() {
		return "TEMPERATURE";
	}

	@Override
	public boolean supportsArithmetic() {
		return supportsArithmetic.isSupported();
	}

	@Override
	public void validateOperationSupport(String operation) {
		throw new UnsupportedOperationException("Temperature does not support " + operation);
	}
}