package com.simplearn.calculation.controller;



import com.simplearn.calculation.dto.FareRequest;
import com.simplearn.calculation.dto.FareResponse;
import com.simplearn.calculation.service.FareCalculationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/fare")
public class FareController {

    private final FareCalculationService fareCalculationService;

    @Autowired
    public FareController(FareCalculationService fareCalculationService) {
        this.fareCalculationService = fareCalculationService;
    }

    /**
     * POST endpoint for calculating fare
     */
    @PostMapping("/calculate")
    public ResponseEntity<FareResponse> calculateFare(@Valid @RequestBody FareRequest request) {
        FareResponse response = fareCalculationService.calculateFare(request);
        return ResponseEntity.ok(response);
    }

    /**
     * GET endpoint to check service health
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Fare Calculation Service is running!");
    }

    /**
     * Handles validation errors for request DTOs
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        Map<String, String> fieldErrors = new HashMap<>();

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }

        errorResponse.put("error", "Validation failed");
        errorResponse.put("message", "Please check the required fields");
        errorResponse.put("fieldErrors", fieldErrors);
        errorResponse.put("timestamp", Instant.now().toString());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Fallback error handler
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Fare calculation failed");
        errorResponse.put("message", "Unable to calculate fare at this time. Please try again later.");
        errorResponse.put("timestamp", Instant.now().toString());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
