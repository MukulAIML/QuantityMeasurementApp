package com.apps.measurement.service;

import com.apps.measurement.dto.QuantityDTO;
import com.apps.measurement.dto.QuantityInputDTO;
import com.apps.measurement.entity.QuantityMeasurementEntity;
import com.apps.measurement.exception.QuantityMeasurementException;
import com.apps.measurement.repository.QuantityMeasurementRepository;
import com.apps.measurement.unit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuantityMeasurementServiceImpl implements IQuantityMeasurementService {

    @Autowired
    private QuantityMeasurementRepository repository;

    private IMeasurable getUnit(String measurementType, String unit) {
        return switch (measurementType.toUpperCase()) {
            case "LENGTH"      -> LengthUnit.valueOf(unit.toUpperCase());
            case "WEIGHT"      -> WeightUnit.valueOf(unit.toUpperCase());
            case "VOLUME"      -> VolumeUnit.valueOf(unit.toUpperCase());
            case "TEMPERATURE" -> TemperatureUnit.valueOf(unit.toUpperCase());
            default -> throw new QuantityMeasurementException("Invalid measurement type: " + measurementType);
        };
    }

    private void setCommonFields(QuantityMeasurementEntity entity, QuantityInputDTO input, String username) {
        QuantityDTO q1 = input.getThisQuantityDTO();
        QuantityDTO q2 = input.getThatQuantityDTO();
        entity.setUsername(username);
        entity.setThisValue(q1.getValue());
        entity.setThisUnit(q1.getUnit());
        entity.setThisMeasurementType(q1.getMeasurementType());
        if (q2 != null) {
            entity.setThatValue(q2.getValue());
            entity.setThatUnit(q2.getUnit());
            entity.setThatMeasurementType(q2.getMeasurementType());
        }
        entity.setCreatedAt(LocalDateTime.now());
    }

    @Override
    public QuantityMeasurementEntity compare(QuantityInputDTO input, String username) {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
        try {
            QuantityDTO q1 = input.getThisQuantityDTO();
            QuantityDTO q2 = input.getThatQuantityDTO();
            IMeasurable unit1 = getUnit(q1.getMeasurementType(), q1.getUnit());
            IMeasurable unit2 = getUnit(q2.getMeasurementType(), q2.getUnit());
            double base1 = unit1.convertToBaseUnit(q1.getValue());
            double base2 = unit2.convertToBaseUnit(q2.getValue());
            setCommonFields(entity, input, username);
            entity.setOperation("COMPARE");
            entity.setResultString(String.valueOf(base1 == base2));
        } catch (Exception e) {
            entity.setUsername(username);
            entity.setError(true);
            entity.setErrorMessage(e.getMessage());
            entity.setOperation("COMPARE");
            entity.setCreatedAt(LocalDateTime.now());
        }
        return repository.save(entity);
    }

    @Override
    public QuantityMeasurementEntity convert(QuantityInputDTO input, String username) {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
        try {
            QuantityDTO q = input.getThisQuantityDTO();
            QuantityDTO target = input.getThatQuantityDTO();
            IMeasurable fromUnit = getUnit(q.getMeasurementType(), q.getUnit());
            IMeasurable toUnit   = getUnit(q.getMeasurementType(), target.getUnit());
            double base   = fromUnit.convertToBaseUnit(q.getValue());
            double result = toUnit.convertFromBaseUnit(base);
            entity.setUsername(username);
            entity.setThisValue(q.getValue());
            entity.setThisUnit(q.getUnit());
            entity.setThisMeasurementType(q.getMeasurementType());
            entity.setOperation("CONVERT");
            entity.setResultValue(result);
            entity.setResultUnit(target.getUnit());
            entity.setResultString(q.getValue() + " " + q.getUnit() + " = " + result + " " + target.getUnit());
            entity.setCreatedAt(LocalDateTime.now());
        } catch (Exception e) {
            entity.setUsername(username);
            entity.setError(true);
            entity.setErrorMessage(e.getMessage());
            entity.setOperation("CONVERT");
            entity.setCreatedAt(LocalDateTime.now());
        }
        return repository.save(entity);
    }

    @Override
    public QuantityMeasurementEntity add(QuantityInputDTO input, String username) {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
        try {
            QuantityDTO q1 = input.getThisQuantityDTO();
            QuantityDTO q2 = input.getThatQuantityDTO();
            IMeasurable unit1 = getUnit(q1.getMeasurementType(), q1.getUnit());
            IMeasurable unit2 = getUnit(q2.getMeasurementType(), q2.getUnit());
            if (!unit1.supportsArithmetic() || !unit2.supportsArithmetic())
                throw new QuantityMeasurementException("Arithmetic not supported for this unit");
            double base1  = unit1.convertToBaseUnit(q1.getValue());
            double base2  = unit2.convertToBaseUnit(q2.getValue());
            double result = unit1.convertFromBaseUnit(base1 + base2);
            setCommonFields(entity, input, username);
            entity.setOperation("ADD");
            entity.setResultValue(result);
            entity.setResultUnit(q1.getUnit());
            entity.setResultString(q1.getValue() + " " + q1.getUnit() + " + " + q2.getValue() + " " + q2.getUnit() + " = " + result + " " + q1.getUnit());
        } catch (Exception e) {
            entity.setUsername(username);
            entity.setError(true);
            entity.setErrorMessage(e.getMessage());
            entity.setOperation("ADD");
            entity.setCreatedAt(LocalDateTime.now());
        }
        return repository.save(entity);
    }

    @Override
    public QuantityMeasurementEntity subtract(QuantityInputDTO input, String username) {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
        try {
            QuantityDTO q1 = input.getThisQuantityDTO();
            QuantityDTO q2 = input.getThatQuantityDTO();
            IMeasurable unit1 = getUnit(q1.getMeasurementType(), q1.getUnit());
            IMeasurable unit2 = getUnit(q2.getMeasurementType(), q2.getUnit());
            if (!unit1.supportsArithmetic() || !unit2.supportsArithmetic())
                throw new QuantityMeasurementException("Arithmetic not supported for this unit");
            double base1  = unit1.convertToBaseUnit(q1.getValue());
            double base2  = unit2.convertToBaseUnit(q2.getValue());
            double result = unit1.convertFromBaseUnit(base1 - base2);
            setCommonFields(entity, input, username);
            entity.setOperation("SUBTRACT");
            entity.setResultValue(result);
            entity.setResultUnit(q1.getUnit());
            entity.setResultString(q1.getValue() + " " + q1.getUnit() + " - " + q2.getValue() + " " + q2.getUnit() + " = " + result + " " + q1.getUnit());
        } catch (Exception e) {
            entity.setUsername(username);
            entity.setError(true);
            entity.setErrorMessage(e.getMessage());
            entity.setOperation("SUBTRACT");
            entity.setCreatedAt(LocalDateTime.now());
        }
        return repository.save(entity);
    }

    @Override
    public QuantityMeasurementEntity divide(QuantityInputDTO input, String username) {
        QuantityMeasurementEntity entity = new QuantityMeasurementEntity();
        try {
            QuantityDTO q1 = input.getThisQuantityDTO();
            QuantityDTO q2 = input.getThatQuantityDTO();
            IMeasurable unit1 = getUnit(q1.getMeasurementType(), q1.getUnit());
            IMeasurable unit2 = getUnit(q2.getMeasurementType(), q2.getUnit());
            if (!unit1.supportsArithmetic() || !unit2.supportsArithmetic())
                throw new QuantityMeasurementException("Arithmetic not supported for this unit");
            double base1 = unit1.convertToBaseUnit(q1.getValue());
            double base2 = unit2.convertToBaseUnit(q2.getValue());
            if (base2 == 0) throw new QuantityMeasurementException("Cannot divide by zero");
            double divResult = base1 / base2;
            setCommonFields(entity, input, username);
            entity.setOperation("DIVIDE");
            entity.setResultValue(divResult);
            entity.setResultUnit("RATIO");
            entity.setResultString(q1.getValue() + " " + q1.getUnit() + " ÷ " + q2.getValue() + " " + q2.getUnit() + " = " + divResult);
        } catch (Exception e) {
            entity.setUsername(username);
            entity.setError(true);
            entity.setErrorMessage(e.getMessage());
            entity.setOperation("DIVIDE");
            entity.setCreatedAt(LocalDateTime.now());
        }
        return repository.save(entity);
    }

    @Override
    public List<QuantityMeasurementEntity> getHistory(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public List<QuantityMeasurementEntity> getHistoryByOperation(String operation, String username) {
        return repository.findByUsernameAndOperation(username, operation.toUpperCase());
    }

    @Override
    public long getOperationCount(String operation, String username) {
        return repository.countByUsernameAndOperationAndErrorFalse(username, operation.toUpperCase());
    }
}
