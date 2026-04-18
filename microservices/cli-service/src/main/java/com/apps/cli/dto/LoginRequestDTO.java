package com.apps.cli.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/** Sent to /auth/register and /auth/login */
@Data @NoArgsConstructor @AllArgsConstructor
public class LoginRequestDTO {
    private String username;
    private String password;
}
