package com.apps.quantitymeasurement.repository;

import com.apps.quantitymeasurement.entity.QuantityMeasurementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuantityMeasurementRepository extends JpaRepository<QuantityMeasurementEntity, Long> {

	List<QuantityMeasurementEntity> findByOperation(String operation);

	long countByOperationAndErrorFalse(String operation);

	List<QuantityMeasurementEntity> findByErrorTrue();
}