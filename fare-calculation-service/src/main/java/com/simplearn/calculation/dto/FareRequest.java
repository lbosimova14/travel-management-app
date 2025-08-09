package com.simplearn.calculation.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/*
 * DTO for receiving fare calculation request from clients
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FareRequest {
    private String pickupLocation;
    private String dropOffLocation;
    private double distanceInKm;
    private String cabType;
}
