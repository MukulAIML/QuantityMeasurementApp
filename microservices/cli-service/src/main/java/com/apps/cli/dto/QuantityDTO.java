package com.apps.cli.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/** Single quantity – mirrors measurement-service QuantityDTO */
@Data @NoArgsConstructor @AllArgsConstructor
public class QuantityDTO {
    private double value;
    private String unit;
    private String measurementType;
}
