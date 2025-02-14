package com.example.carRental.dto;


import com.example.carRental.model.Car;

public record RentalRequestDTO(Car car, long days) {

}
