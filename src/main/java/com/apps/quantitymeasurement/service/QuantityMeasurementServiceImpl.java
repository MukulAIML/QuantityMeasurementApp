package com.apps.quantitymeasurement.service;

import com.apps.quantitymeasurement.Quantity;
import com.apps.quantitymeasurement.entity.QuantityDTO;
import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.apps.quantitymeasurement.entity.QuantityModel;
import com.apps.quantitymeasurement.exception.QuantityMeasurementException;
import com.apps.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.apps.quantitymeasurement.unit.IMeasurable;

import java.util.logging.Logger;

public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

	private static final Logger logger = Logger.getLogger(QuantityMeasurementServiceImpl.class.getName());

	private final IQuantityMeasurementRepository repository;

	public QuantityMeasurementServiceImpl(IQuantityMeasurementRepository repository) {
		this.repository = repository;
		logger.info("Service initialized");
	}

	@SuppressWarnings("unchecked")
	private <U extends IMeasurable> QuantityModel<U> toModel(QuantityDTO dto) {
		U unit = (U) IMeasurable.getUnitByName(dto.getMeasurementType(), dto.getUnitName());
		return new QuantityModel<>(dto.getValue(), unit);
	}

	private QuantityDTO.IMeasurableUnit resolveInnerUnit(String measurementType, String unitName) {
		switch (measurementType.toUpperCase()) {
		case "LENGTH":
			return QuantityDTO.LengthUnit.valueOf(unitName.toUpperCase());
		case "WEIGHT":
			return QuantityDTO.WeightUnit.valueOf(unitName.toUpperCase());
		case "VOLUME":
			return QuantityDTO.VolumeUnit.valueOf(unitName.toUpperCase());
		case "TEMPERATURE":
			return QuantityDTO.TemperatureUnit.valueOf(unitName.toUpperCase());
		default:
			throw new QuantityMeasurementException("Unknown measurement type: " + measurementType);
		}
	}

	@Override
	public boolean compare(QuantityDTO dto1, QuantityDTO dto2) {
		try {
			QuantityModel<?> m1 = toModel(dto1);
			QuantityModel<?> m2 = toModel(dto2);
			boolean result = m1.toQuantity().equals(m2.toQuantity());
			repository.save(
					new QuantityMeasurementEntity(dto1.toString(), dto2.toString(), "COMPARE", String.valueOf(result)));
			return result;
		} catch (Exception e) {
			repository.save(
					new QuantityMeasurementEntity(dto1.toString(), dto2.toString(), "COMPARE", e.getMessage(), true));
			throw new QuantityMeasurementException("Comparison failed: " + e.getMessage(), e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public QuantityDTO convert(QuantityDTO dto, String targetUnit) {
		try {
			QuantityModel model = toModel(dto);
			IMeasurable target = IMeasurable.getUnitByName(dto.getMeasurementType(), targetUnit);
			Quantity result = model.toQuantity().convertTo(target);
			QuantityDTO resultDTO = new QuantityDTO(result.getValue(),
					resolveInnerUnit(dto.getMeasurementType(), targetUnit));
			repository.save(new QuantityMeasurementEntity(dto.toString(), "CONVERT", resultDTO.toString()));
			return resultDTO;
		} catch (Exception e) {
			repository.save(new QuantityMeasurementEntity(dto.toString(), null, "CONVERT", e.getMessage(), true));
			throw new QuantityMeasurementException("Conversion failed: " + e.getMessage(), e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public QuantityDTO add(QuantityDTO dto1, QuantityDTO dto2, String targetUnit) {
		try {
			QuantityModel m1 = toModel(dto1);
			QuantityModel m2 = toModel(dto2);
			IMeasurable target = IMeasurable.getUnitByName(dto1.getMeasurementType(), targetUnit);
			Quantity result = m1.toQuantity().add(m2.toQuantity(), target);
			QuantityDTO resultDTO = new QuantityDTO(result.getValue(),
					resolveInnerUnit(dto1.getMeasurementType(), targetUnit));
			repository
					.save(new QuantityMeasurementEntity(dto1.toString(), dto2.toString(), "ADD", resultDTO.toString()));
			return resultDTO;
		} catch (Exception e) {
			repository
					.save(new QuantityMeasurementEntity(dto1.toString(), dto2.toString(), "ADD", e.getMessage(), true));
			throw new QuantityMeasurementException("Add failed: " + e.getMessage(), e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public QuantityDTO subtract(QuantityDTO dto1, QuantityDTO dto2, String targetUnit) {
		try {
			QuantityModel m1 = toModel(dto1);
			QuantityModel m2 = toModel(dto2);
			IMeasurable target = IMeasurable.getUnitByName(dto1.getMeasurementType(), targetUnit);
			Quantity result = m1.toQuantity().subtract(m2.toQuantity(), target);
			QuantityDTO resultDTO = new QuantityDTO(result.getValue(),
					resolveInnerUnit(dto1.getMeasurementType(), targetUnit));
			repository.save(
					new QuantityMeasurementEntity(dto1.toString(), dto2.toString(), "SUBTRACT", resultDTO.toString()));
			return resultDTO;
		} catch (Exception e) {
			repository.save(
					new QuantityMeasurementEntity(dto1.toString(), dto2.toString(), "SUBTRACT", e.getMessage(), true));
			throw new QuantityMeasurementException("Subtract failed: " + e.getMessage(), e);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public double divide(QuantityDTO dto1, QuantityDTO dto2) {
		try {
			QuantityModel m1 = toModel(dto1);
			QuantityModel m2 = toModel(dto2);
			double result = m1.toQuantity().divide(m2.toQuantity());
			repository.save(
					new QuantityMeasurementEntity(dto1.toString(), dto2.toString(), "DIVIDE", String.valueOf(result)));
			return result;
		} catch (Exception e) {
			repository.save(
					new QuantityMeasurementEntity(dto1.toString(), dto2.toString(), "DIVIDE", e.getMessage(), true));
			throw new QuantityMeasurementException("Divide failed: " + e.getMessage(), e);
		}
	}
}