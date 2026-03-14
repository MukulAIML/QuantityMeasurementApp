package com.apps.quantitymeasurement.controller;

import com.apps.quantitymeasurement.entity.QuantityDTO;
import com.apps.quantitymeasurement.exception.QuantityMeasurementException;
import com.apps.quantitymeasurement.service.IQuantityMeasurementService;

import java.util.logging.Logger;

public class QuantityMeasurementController {

	private static final Logger logger = Logger.getLogger(QuantityMeasurementController.class.getName());

	private final IQuantityMeasurementService service;

	public QuantityMeasurementController(IQuantityMeasurementService service) {
		this.service = service;
		logger.info("Controller initialized");
	}

	public boolean performComparison(QuantityDTO dto1, QuantityDTO dto2) {
		try {
			boolean result = service.compare(dto1, dto2);
			logger.info("compare(" + dto1 + ", " + dto2 + ") = " + result);
			return result;
		} catch (QuantityMeasurementException e) {
			logger.warning("compare failed: " + e.getMessage());
			return false;
		}
	}

	public QuantityDTO performConversion(QuantityDTO dto, String targetUnit) {
		try {
			QuantityDTO result = service.convert(dto, targetUnit);
			logger.info("convert(" + dto + ", " + targetUnit + ") = " + result);
			return result;
		} catch (QuantityMeasurementException e) {
			logger.warning("convert failed: " + e.getMessage());
			return null;
		}
	}

	public QuantityDTO performAddition(QuantityDTO dto1, QuantityDTO dto2, String targetUnit) {
		try {
			QuantityDTO result = service.add(dto1, dto2, targetUnit);
			logger.info("add(" + dto1 + ", " + dto2 + ", " + targetUnit + ") = " + result);
			return result;
		} catch (QuantityMeasurementException e) {
			logger.warning("add failed: " + e.getMessage());
			return null;
		}
	}

	public QuantityDTO performSubtraction(QuantityDTO dto1, QuantityDTO dto2, String targetUnit) {
		try {
			QuantityDTO result = service.subtract(dto1, dto2, targetUnit);
			logger.info("subtract(" + dto1 + ", " + dto2 + ", " + targetUnit + ") = " + result);
			return result;
		} catch (QuantityMeasurementException e) {
			logger.warning("subtract failed: " + e.getMessage());
			return null;
		}
	}

	public double performDivision(QuantityDTO dto1, QuantityDTO dto2) {
		try {
			double result = service.divide(dto1, dto2);
			logger.info("divide(" + dto1 + ", " + dto2 + ") = " + result);
			return result;
		} catch (QuantityMeasurementException e) {
			logger.warning("divide failed: " + e.getMessage());
			return Double.NaN;
		}
	}
}