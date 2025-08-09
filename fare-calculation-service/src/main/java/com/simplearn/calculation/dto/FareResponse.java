package com.simplearn.calculation.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/*
 * DTO for returning calculated fare back to the client
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FareResponse {
    private double totalFare;
}
