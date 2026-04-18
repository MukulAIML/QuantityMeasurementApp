package com.apps.gateway.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth Gateway", description = "Proxies auth requests to auth-service")
public class AuthProxyController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("authServiceUrl")
    private String authServiceUrl;

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<String> register(@RequestBody Object body) {
        return proxy(HttpMethod.POST, authServiceUrl + "/auth/register", body, null);
    }

    @PostMapping("/login")
    @Operation(summary = "Login and receive JWT token")
    public ResponseEntity<String> login(@RequestBody Object body) {
        return proxy(HttpMethod.POST, authServiceUrl + "/auth/login", body, null);
    }

    private ResponseEntity<String> proxy(HttpMethod method, String url, Object body, String authHeader) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            if (authHeader != null) headers.set("Authorization", authHeader);
            HttpEntity<Object> entity = new HttpEntity<>(body, headers);
            ResponseEntity<String> resp = restTemplate.exchange(url, method, entity, String.class);
            return ResponseEntity.status(resp.getStatusCode())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(resp.getBody());
        } catch (HttpClientErrorException ex) {
            return ResponseEntity.status(ex.getStatusCode())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(ex.getResponseBodyAsString());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("{\"message\":\"Auth service unavailable: " + ex.getMessage() + "\"}");
        }
    }
}

