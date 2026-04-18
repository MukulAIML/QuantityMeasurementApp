package com.apps.cli.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Received from /auth/login */
@Data @NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthResponseDTO {
    private String token;
    private String username;
}
