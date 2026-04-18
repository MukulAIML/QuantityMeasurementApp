package com.apps.measurement.service;

import com.apps.measurement.dto.QuantityInputDTO;
import com.apps.measurement.entity.QuantityMeasurementEntity;

import java.util.List;

public interface IQuantityMeasurementService {
    QuantityMeasurementEntity compare(QuantityInputDTO input, String username);
    QuantityMeasurementEntity convert(QuantityInputDTO input, String username);
    QuantityMeasurementEntity add(QuantityInputDTO input, String username);
    QuantityMeasurementEntity subtract(QuantityInputDTO input, String username);
    QuantityMeasurementEntity divide(QuantityInputDTO input, String username);
    List<QuantityMeasurementEntity> getHistory(String username);
    List<QuantityMeasurementEntity> getHistoryByOperation(String operation, String username);
    long getOperationCount(String operation, String username);
}
