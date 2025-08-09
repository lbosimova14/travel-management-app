package com.simplearn.booking.service;


import com.simplearn.booking.dto.CabBookingRequest;
import com.simplearn.booking.dto.CabBookingResponse;
import com.simplearn.booking.dto.FareCalculationRequest;
import com.simplearn.booking.dto.FareResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CabBookingService {

    private final RestTemplate restTemplate;
    private final Map<Long, CabBookingResponse> bookings = new ConcurrentHashMap<>();
    private final AtomicLong bookingIdCounter = new AtomicLong(1);

    @Value("${fare.service-url}")
    private String fareServiceUrl;

    @Autowired
    public CabBookingService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CabBookingResponse createBooking(CabBookingRequest request) {
        Long bookingId = bookingIdCounter.getAndIncrement();

        // Create fare calculation request
        FareCalculationRequest fareRequest = new FareCalculationRequest();
        fareRequest.setPickupLocation(request.getPickupLocation());
        fareRequest.setDropOffLocation(request.getDropOffLocation());
        fareRequest.setDistanceInKm(request.getDistanceInKm());
        fareRequest.setCabType(request.getCabType());

        FareResponse fareResponse = null;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<FareCalculationRequest> entity = new HttpEntity<>(fareRequest, headers);
            ResponseEntity<FareResponse> response = restTemplate.postForEntity(fareServiceUrl, entity, FareResponse.class);
            fareResponse = response.getBody();
        } catch (RestClientException e) {
            System.err.println("Fare service unavailable: " + e.getMessage());
        }

        // Create booking response
        String status = fareResponse != null ? "CONFIRMED" : "PENDING_FARE";
        double fareAmount = fareResponse != null ? fareResponse.getTotalFare() : 0.0;

        CabBookingResponse bookingResponse = new CabBookingResponse(
            bookingId,
            request.getCustomerName(),
            request.getPickupLocation(),
            request.getDropOffLocation(),
            request.getCabType(),
            fareAmount,
            status
        );

        // Store the booking
        bookings.put(bookingId, bookingResponse);

        return bookingResponse;
    }
}
