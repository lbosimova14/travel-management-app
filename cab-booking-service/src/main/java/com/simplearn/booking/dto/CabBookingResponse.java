package com.simplearn.booking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CabBookingResponse {
    private Long bookingId;
    private String customerName;
    private String pickupLocation;
    private String dropOffLocation;
    private String cabType;
    private double fareAmount;
    private String status;
}
