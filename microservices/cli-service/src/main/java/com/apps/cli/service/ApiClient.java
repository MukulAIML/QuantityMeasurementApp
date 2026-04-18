package com.apps.cli.service;

import com.apps.cli.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * HTTP client that mirrors src/services/api.js from the React frontend.
 * All requests go to the api-gateway at ${api.gateway.url}.
 */
@Slf4j
@Service
public class ApiClient {

    private final RestTemplate restTemplate;
    private final SessionState session;
    private final String baseUrl;
    private final ObjectMapper objectMapper;

    public ApiClient(RestTemplate restTemplate,
                     SessionState session,
                     @Value("${api.gateway.url}") String baseUrl,
                     ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.session = session;
        this.baseUrl = baseUrl;
        this.objectMapper = objectMapper;
    }

    // ─── Auth ─────────────────────────────────────────────────────────────

    public AuthResponseDTO register(String username, String password) {
        LoginRequestDTO req = new LoginRequestDTO(username, password);
        return post("/auth/register", req, AuthResponseDTO.class, false);
    }

    public AuthResponseDTO login(String username, String password) {
        LoginRequestDTO req = new LoginRequestDTO(username, password);
        AuthResponseDTO resp = post("/auth/login", req, AuthResponseDTO.class, false);
        if (resp != null && resp.getToken() != null) {
            session.setSession(resp.getToken(), username);
        }
        return resp;
    }

    public void logout() {
        session.clearSession();
    }

    // ─── Quantity Operations ───────────────────────────────────────────────

    public Object compare(QuantityInputDTO body) {
        return post("/api/v1/quantities/compare", body, Object.class, true);
    }

    public Object convert(QuantityInputDTO body) {
        return post("/api/v1/quantities/convert", body, Object.class, true);
    }

    public Object add(QuantityInputDTO body) {
        return post("/api/v1/quantities/add", body, Object.class, true);
    }

    public Object subtract(QuantityInputDTO body) {
        return post("/api/v1/quantities/subtract", body, Object.class, true);
    }

    public Object divide(QuantityInputDTO body) {
        return post("/api/v1/quantities/divide", body, Object.class, true);
    }

    // ─── History ──────────────────────────────────────────────────────────

    public Object getHistory() {
        return get("/api/v1/quantities/history");
    }

    public Object getHistoryByOperation(String operation) {
        return get("/api/v1/quantities/history/" + operation);
    }

    public Object getOperationCount(String operation) {
        return get("/api/v1/quantities/count/" + operation);
    }

    // ─── Helpers ──────────────────────────────────────────────────────────

    private <T> T post(String path, Object body, Class<T> responseType, boolean requiresAuth) {
        try {
            HttpEntity<Object> entity = new HttpEntity<>(body, buildHeaders(requiresAuth));
            ResponseEntity<T> response = restTemplate.exchange(
                    baseUrl + path, HttpMethod.POST, entity, responseType);
            return response.getBody();
        } catch (HttpClientErrorException ex) {
            throw new RuntimeException(parseErrorMessage(ex));
        }
    }

    private Object get(String path) {
        try {
            HttpEntity<Void> entity = new HttpEntity<>(buildHeaders(true));
            ResponseEntity<Object> response = restTemplate.exchange(
                    baseUrl + path, HttpMethod.GET, entity, Object.class);
            return response.getBody();
        } catch (HttpClientErrorException ex) {
            throw new RuntimeException(parseErrorMessage(ex));
        }
    }

    private HttpHeaders buildHeaders(boolean requiresAuth) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (requiresAuth && session.isLoggedIn()) {
            headers.set("Authorization", session.bearerToken());
        }
        return headers;
    }

    private String parseErrorMessage(HttpClientErrorException ex) {
        try {
            var node = objectMapper.readTree(ex.getResponseBodyAsString());
            if (node.has("message")) return node.get("message").asText();
            if (node.has("error"))   return node.get("error").asText();
        } catch (Exception ignored) { /* fall through */ }
        return ex.getStatusCode() + ": " + ex.getResponseBodyAsString();
    }

    /** Convenience factory for QuantityInputDTO */
    public static QuantityInputDTO buildPayload(
            double value1, String unit1, String type1,
            double value2, String unit2, String type2) {
        return new QuantityInputDTO(
                new com.apps.cli.dto.QuantityDTO(value1, unit1, type1),
                new com.apps.cli.dto.QuantityDTO(value2, unit2, type2));
    }
}
