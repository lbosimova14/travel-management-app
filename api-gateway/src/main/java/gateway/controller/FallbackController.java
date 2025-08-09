package gateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * LESSON NOTE: Circuit Breaker Fallback Controller
 *
 * Handles graceful degradation when microservices like Order, Pricing, or Fare are unavailable.
 */
@RestController
public class FallbackController {

    /**
     * Fallback for Fare Service (Cab Booking)
     */
    @RequestMapping("/fallback/fare")
    public ResponseEntity<Map<String, Object>> fareServiceFallback() {
        return createFallbackResponse(
                "Fare Service Temporarily Unavailable",
                "We are unable to calculate cab fare at the moment. Please try again soon.",
                "FARE_SERVICE_DOWN"
        );
    }

    @RequestMapping("/fallback/booking")
    public ResponseEntity<Map<String, Object>> bookingServiceFallback() {
        return createFallbackResponse(
                "Booking Service Temporarily Unavailable",
                "We're unable to process bookings at the moment. Please try again later.",
                "BOOKING_SERVICE_DOWN"
        );
    }

    /**
     * Standardized fallback response
     */
    private ResponseEntity<Map<String, Object>> createFallbackResponse(String title, String message, String errorCode) {
        Map<String, Object> response = new HashMap<>();
        response.put("error", true);
        response.put("title", title);
        response.put("message", message);
        response.put("errorCode", errorCode);
        response.put("timestamp", LocalDateTime.now());
        response.put("suggestion", "This is a temporary issue. The service will be restored shortly.");

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(response);
    }
}
