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
@RequestMapping("/api/v1/quantities")
@Tag(name = "Measurement Gateway", description = "Proxies measurement requests to measurement-service")
public class MeasurementProxyController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    @Qualifier("measurementServiceUrl")
    private String measurementServiceUrl;

    @PostMapping("/compare")
    @Operation(summary = "Compare two quantities")
    public ResponseEntity<String> compare(@RequestBody Object body, HttpServletRequest request) {
        return proxy(HttpMethod.POST, measurementServiceUrl + "/api/v1/quantities/compare", body, request);
    }

    @PostMapping("/convert")
    @Operation(summary = "Convert a quantity to another unit")
    public ResponseEntity<String> convert(@RequestBody Object body, HttpServletRequest request) {
        return proxy(HttpMethod.POST, measurementServiceUrl + "/api/v1/quantities/convert", body, request);
    }

    @PostMapping("/add")
    @Operation(summary = "Add two quantities")
    public ResponseEntity<String> add(@RequestBody Object body, HttpServletRequest request) {
        return proxy(HttpMethod.POST, measurementServiceUrl + "/api/v1/quantities/add", body, request);
    }

    @PostMapping("/subtract")
    @Operation(summary = "Subtract two quantities")
    public ResponseEntity<String> subtract(@RequestBody Object body, HttpServletRequest request) {
        return proxy(HttpMethod.POST, measurementServiceUrl + "/api/v1/quantities/subtract", body, request);
    }

    @PostMapping("/divide")
    @Operation(summary = "Divide two quantities")
    public ResponseEntity<String> divide(@RequestBody Object body, HttpServletRequest request) {
        return proxy(HttpMethod.POST, measurementServiceUrl + "/api/v1/quantities/divide", body, request);
    }

    @GetMapping("/history")
    @Operation(summary = "Get all operation history")
    public ResponseEntity<String> getHistory(HttpServletRequest request) {
        return proxy(HttpMethod.GET, measurementServiceUrl + "/api/v1/quantities/history", null, request);
    }

    @GetMapping("/history/{operation}")
    @Operation(summary = "Get history by operation type")
    public ResponseEntity<String> getHistoryByOperation(@PathVariable String operation, HttpServletRequest request) {
        return proxy(HttpMethod.GET, measurementServiceUrl + "/api/v1/quantities/history/" + operation, null, request);
    }

    @GetMapping("/count/{operation}")
    @Operation(summary = "Get count of successful operations by type")
    public ResponseEntity<String> getOperationCount(@PathVariable String operation, HttpServletRequest request) {
        return proxy(HttpMethod.GET, measurementServiceUrl + "/api/v1/quantities/count/" + operation, null, request);
    }

    private ResponseEntity<String> proxy(HttpMethod method, String url, Object body, HttpServletRequest request) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null) headers.set("Authorization", authHeader);
            HttpEntity<Object> entity = new HttpEntity<>(body, headers);
            ResponseEntity<String> resp = restTemplate.exchange(url, method, entity, String.class);
            return ResponseEntity.status(resp.getStatusCode())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(resp.getBody());
        } catch (HttpClientErrorException ex) {
            return ResponseEntity.status(ex.getStatusCode()).body(ex.getResponseBodyAsString());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body("{\"message\":\"Measurement service unavailable: " + ex.getMessage() + "\"}");
        }
    }
}

