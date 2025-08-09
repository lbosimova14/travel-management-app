package com.simplearn.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FareCalculationRequest {
    private String pickupLocation;
    private String dropOffLocation;
    private double distanceInKm;
    private String cabType;
}
