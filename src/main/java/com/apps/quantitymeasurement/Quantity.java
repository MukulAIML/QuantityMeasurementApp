package com.apps.quantitymeasurement;

import com.apps.quantitymeasurement.unit.IMeasurable;
import java.util.function.DoubleBinaryOperator;

public class Quantity<U extends IMeasurable> {

	private final double value;
	private final U unit;

	public Quantity(double value, U unit) {
		if (unit == null)
			throw new IllegalArgumentException("Unit cannot be null");
		if (!Double.isFinite(value))
			throw new IllegalArgumentException("Value must be finite");
		this.value = value;
		this.unit = unit;
	}

	public double getValue() {
		return value;
	}

	public U getUnit() {
		return unit;
	}

	public <T extends IMeasurable> Quantity<T> convertTo(T targetUnit) {
		double base = unit.convertToBaseUnit(value);
		double converted = targetUnit.convertFromBaseUnit(base);
		return new Quantity<>(roundToTwoDecimals(converted), targetUnit);
	}

	public Quantity<U> add(Quantity<U> other) {
		return add(other, this.unit);
	}

	public Quantity<U> add(Quantity<U> other, U targetUnit) {
		validateArithmeticOperands(other, targetUnit, true);
		double result = performBaseArithmetic(other, ArithmeticOperation.ADD);
		double converted = targetUnit.convertFromBaseUnit(result);
		return new Quantity<>(roundToTwoDecimals(converted), targetUnit);
	}

	public Quantity<U> subtract(Quantity<U> other) {
		return subtract(other, this.unit);
	}

	public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
		validateArithmeticOperands(other, targetUnit, true);
		double result = performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);
		double converted = targetUnit.convertFromBaseUnit(result);
		return new Quantity<>(roundToTwoDecimals(converted), targetUnit);
	}

	public double divide(Quantity<U> other) {
		validateArithmeticOperands(other, this.unit, false);
		return performBaseArithmetic(other, ArithmeticOperation.DIVIDE);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Quantity))
			return false;
		Quantity<?> other = (Quantity<?>) obj;
		if (!this.unit.getMeasurementType().equals(other.unit.getMeasurementType()))
			return false;
		double thisBase = this.unit.convertToBaseUnit(this.value);
		double otherBase = other.unit.convertToBaseUnit(other.value);
		return Math.abs(thisBase - otherBase) < 1e-9;
	}

	@Override
	public int hashCode() {
		return Double.hashCode(roundToTwoDecimals(unit.convertToBaseUnit(value)));
	}

	@Override
	public String toString() {
		return value + " " + unit.getUnitName();
	}

	private void validateArithmeticOperands(Quantity<U> other, U targetUnit, boolean targetUnitRequired) {
		if (other == null)
			throw new IllegalArgumentException("Operand cannot be null");
		if (targetUnitRequired && targetUnit == null)
			throw new IllegalArgumentException("Target unit cannot be null");
		if (this.unit.getClass() != other.unit.getClass())
			throw new IllegalArgumentException("Cannot operate on different measurement types");
		if (!Double.isFinite(other.value))
			throw new IllegalArgumentException("Operand value must be finite");
		this.unit.validateOperationSupport("ADD");
	}

	private double performBaseArithmetic(Quantity<U> other, ArithmeticOperation operation) {
		double base1 = this.unit.convertToBaseUnit(this.value);
		double base2 = other.unit.convertToBaseUnit(other.value);
		return operation.compute(base1, base2);
	}

	private double roundToTwoDecimals(double value) {
		return Math.round(value * 100.0) / 100.0;
	}

	private enum ArithmeticOperation {
		ADD((a, b) -> a + b), SUBTRACT((a, b) -> a - b), DIVIDE((a, b) -> {
			if (b == 0)
				throw new ArithmeticException("Division by zero");
			return a / b;
		});

		private final DoubleBinaryOperator operator;

		ArithmeticOperation(DoubleBinaryOperator operator) {
			this.operator = operator;
		}

		public double compute(double a, double b) {
			return operator.applyAsDouble(a, b);
		}
	}
}