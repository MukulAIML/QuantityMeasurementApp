package com.apps.cli.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/** Request body for all quantity operations */
@Data @NoArgsConstructor @AllArgsConstructor
public class QuantityInputDTO {
    private QuantityDTO thisQuantityDTO;
    private QuantityDTO thatQuantityDTO;
}
