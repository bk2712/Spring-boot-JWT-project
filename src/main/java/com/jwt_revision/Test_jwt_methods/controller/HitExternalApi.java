package com.jwt_revision.Test_jwt_methods.controller;

import com.jwt_revision.Test_jwt_methods.model.ApiRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api-client")
public class HitExternalApi {

    @Autowired
    RestTemplate restTemplate;

    @PostMapping("/call")
    public ResponseEntity<?> callExternalApi(@RequestBody ApiRequest request) {
        try {
            // Set headers
            HttpHeaders headers = new HttpHeaders();
            if (request.getHeaders() != null) {
                request.getHeaders().forEach(headers::set);
            }

            // Set body
            HttpEntity<String> entity = new HttpEntity<>(request.getBody(), headers);

            // Choose method
            HttpMethod method = HttpMethod.valueOf(request.getMethod().toUpperCase());

            // Send request
            // restTemplate.exchange ki help se hi request post ki jati h
            ResponseEntity<String> response = restTemplate.exchange(
                    request.getUrl(),
                    method,
                    entity,
                    String.class
            );

            return ResponseEntity.ok(response.getBody());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("API Call failed: " + e.getMessage());
        }
    }
}
