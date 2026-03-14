package com.apps.quantitymeasurement.repository;

import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;

import java.util.List;
import java.util.stream.Collectors;

public interface IQuantityMeasurementRepository {
	void save(QuantityMeasurementEntity entity);

	List<QuantityMeasurementEntity> getAllMeasurements();

	default List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation) {
		return getAllMeasurements().stream().filter(e -> operation.equalsIgnoreCase(e.getOperation()))
				.collect(Collectors.toList());
	}

	default List<QuantityMeasurementEntity> getMeasurementsByType(String type) {
		return getAllMeasurements().stream()
				.filter(e -> e.getOperand1() != null && e.getOperand1().toUpperCase().contains(type.toUpperCase()))
				.collect(Collectors.toList());
	}

	default long getTotalCount() {
		return getAllMeasurements().size();
	}

	default void deleteAll() {
		throw new UnsupportedOperationException("deleteAll not supported by this repository");
	}

	default String getPoolStatistics() {
		return "Pool statistics not available";
	}

	default void releaseResources() {
	}
}