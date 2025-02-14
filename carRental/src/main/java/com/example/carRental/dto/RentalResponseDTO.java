package com.example.carRental.dto;

import com.example.carRental.model.Car;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RentalResponseDTO(String car, String rentalStart, String rentalEnd,
                                BigDecimal price) {


}
