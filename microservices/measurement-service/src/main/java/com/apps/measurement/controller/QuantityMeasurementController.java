package com.apps.measurement.controller;

import com.apps.measurement.dto.QuantityInputDTO;
import com.apps.measurement.entity.QuantityMeasurementEntity;
import com.apps.measurement.service.IQuantityMeasurementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/quantities")
@Tag(name = "Quantity Measurements", description = "REST API for quantity measurement operations")
public class QuantityMeasurementController {

    @Autowired
    private IQuantityMeasurementService service;

    @PostMapping("/compare")
    @Operation(summary = "Compare two quantities")
    public ResponseEntity<QuantityMeasurementEntity> compare(
            @RequestBody QuantityInputDTO input, Authentication auth) {
        return ResponseEntity.ok(service.compare(input, auth.getName()));
    }

    @PostMapping("/convert")
    @Operation(summary = "Convert a quantity to another unit")
    public ResponseEntity<QuantityMeasurementEntity> convert(
            @RequestBody QuantityInputDTO input, Authentication auth) {
        return ResponseEntity.ok(service.convert(input, auth.getName()));
    }

    @PostMapping("/add")
    @Operation(summary = "Add two quantities")
    public ResponseEntity<QuantityMeasurementEntity> add(
            @RequestBody QuantityInputDTO input, Authentication auth) {
        return ResponseEntity.ok(service.add(input, auth.getName()));
    }

    @PostMapping("/subtract")
    @Operation(summary = "Subtract two quantities")
    public ResponseEntity<QuantityMeasurementEntity> subtract(
            @RequestBody QuantityInputDTO input, Authentication auth) {
        return ResponseEntity.ok(service.subtract(input, auth.getName()));
    }

    @PostMapping("/divide")
    @Operation(summary = "Divide two quantities")
    public ResponseEntity<QuantityMeasurementEntity> divide(
            @RequestBody QuantityInputDTO input, Authentication auth) {
        return ResponseEntity.ok(service.divide(input, auth.getName()));
    }

    @GetMapping("/history")
    @Operation(summary = "Get all operation history for the logged-in user")
    public ResponseEntity<List<QuantityMeasurementEntity>> getHistory(Authentication auth) {
        return ResponseEntity.ok(service.getHistory(auth.getName()));
    }

    @GetMapping("/history/{operation}")
    @Operation(summary = "Get history by operation type for the logged-in user")
    public ResponseEntity<List<QuantityMeasurementEntity>> getHistoryByOperation(
            @PathVariable String operation, Authentication auth) {
        return ResponseEntity.ok(service.getHistoryByOperation(operation, auth.getName()));
    }

    @GetMapping("/count/{operation}")
    @Operation(summary = "Get count of successful operations by type for the logged-in user")
    public ResponseEntity<Long> getOperationCount(
            @PathVariable String operation, Authentication auth) {
        return ResponseEntity.ok(service.getOperationCount(operation, auth.getName()));
    }
}
