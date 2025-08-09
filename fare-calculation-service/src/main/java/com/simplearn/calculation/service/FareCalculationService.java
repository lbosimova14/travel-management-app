package com.simplearn.calculation.service;


import com.simplearn.calculation.dto.FareRequest;
import com.simplearn.calculation.dto.FareResponse;
import org.springframework.stereotype.Service;

/*
 * LESSON NOTE: Simplified Fare Calculation Service
 * - Focuses on estimating cab fare based on distance and cab type
 * - Equivalent to pricing service in the food-ordering example
 */
@Service
public class FareCalculationService {

    // Constants for fare calculation
    private static final double BASE_RATE_PER_KM = 1.50;
    private static final double TAX_RATE = 0.07;

    /*
     * LESSON NOTE: Main Fare Calculation Method
     * - Uses distance and cab type to estimate fare
     * - Demonstrates same microservice calculation pattern
     */
    public FareResponse calculateFare(FareRequest request) {
        double distance = request.getDistanceInKm();
        String cabType = request.getCabType();

        // Base fare
        double baseFare = distance * BASE_RATE_PER_KM;

        // Adjust fare based on cab type
        double cabTypeMultiplier = getCabTypeMultiplier(cabType);
        double adjustedFare = baseFare * cabTypeMultiplier;

        // Tax calculation
        double tax = adjustedFare * TAX_RATE;
        double totalAmount = adjustedFare + tax;

        return new FareResponse(totalAmount);
    }

    /*
     * LESSON NOTE: Cab Type Multiplier
     * - Simulates surge pricing or cab quality level
     */
    private double getCabTypeMultiplier(String cabType) {
        if (cabType == null) return 1.0;

        switch (cabType.toLowerCase()) {
            case "suv": return 1.8;
            case "luxury": return 2.5;
            case "mini": return 1.2;
            case "sedan": return 1.5;
            default: return 1.0; // standard
        }
    }
}
