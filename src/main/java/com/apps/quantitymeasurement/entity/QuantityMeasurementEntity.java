package com.apps.quantitymeasurement.entity;

import java.io.Serializable;

public class QuantityMeasurementEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String operand1;
	private String operand2;
	private String operation;
	private String result;
	private boolean hasError;
	private String errorMessage;

	public QuantityMeasurementEntity(String operand1, String operation, String result) {
		this.operand1 = operand1;
		this.operation = operation;
		this.result = result;
		this.hasError = false;
	}

	public QuantityMeasurementEntity(String operand1, String operand2, String operation, String result) {
		this.operand1 = operand1;
		this.operand2 = operand2;
		this.operation = operation;
		this.result = result;
		this.hasError = false;
	}

	public QuantityMeasurementEntity(String operand1, String operand2, String operation, String errorMessage,
			boolean hasError) {
		this.operand1 = operand1;
		this.operand2 = operand2;
		this.operation = operation;
		this.errorMessage = errorMessage;
		this.hasError = hasError;
	}

	public String getOperand1() {
		return operand1;
	}

	public String getOperand2() {
		return operand2;
	}

	public String getOperation() {
		return operation;
	}

	public String getResult() {
		return result;
	}

	public boolean hasError() {
		return hasError;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	@Override
	public String toString() {
		if (hasError) {
			return "[ERROR] " + operation + "(" + operand1 + (operand2 != null ? ", " + operand2 : "") + ") -> "
					+ errorMessage;
		}
		return operation + "(" + operand1 + (operand2 != null ? ", " + operand2 : "") + ") -> " + result;
	}
}