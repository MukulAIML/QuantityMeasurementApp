package com.apps.measurement.repository;

import com.apps.measurement.entity.QuantityMeasurementEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuantityMeasurementRepository extends JpaRepository<QuantityMeasurementEntity, Long> {

    // Per-user queries (used in normal flow)
    List<QuantityMeasurementEntity> findByUsername(String username);
    List<QuantityMeasurementEntity> findByUsernameAndOperation(String username, String operation);
    long countByUsernameAndOperationAndErrorFalse(String username, String operation);

    // Fallback / admin queries
    List<QuantityMeasurementEntity> findByOperation(String operation);
    long countByOperationAndErrorFalse(String operation);
    List<QuantityMeasurementEntity> findByErrorTrue();
}
