package com.simplearn.booking.controller;



import com.simplearn.booking.dto.CabBookingRequest;
import com.simplearn.booking.dto.CabBookingResponse;
import com.simplearn.booking.service.CabBookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/bookings")
public class CabBookingController {

    private final CabBookingService cabBookingService;

    @Autowired
    public CabBookingController(CabBookingService cabBookingService) {
        this.cabBookingService = cabBookingService;
    }

    /**
     * Handles cab booking creation
     * @param request CabBookingRequest with customer, locations, etc.
     * @return CabBookingResponse with fare and booking status
     */
    @PostMapping
    public ResponseEntity<CabBookingResponse> createBooking(@Valid @RequestBody CabBookingRequest request) {
        CabBookingResponse response = cabBookingService.createBooking(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Cab Booking Service is running!");
    }

    /**
     * Validation error handler
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException e) {
        Map<String, Object> errorResponse = new HashMap<>();
        Map<String, String> fieldErrors = new HashMap<>();

        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        }

        errorResponse.put("error", "Validation failed");
        errorResponse.put("message", "Please check the required fields");
        errorResponse.put("fieldErrors", fieldErrors);
        errorResponse.put("timestamp", Instant.now().toString());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * General error handler
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception e) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("error", "Booking failed");
        errorResponse.put("message", "Unable to process booking at this time. Please try again later.");
        errorResponse.put("timestamp", Instant.now().toString());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorResponse);
    }
}
