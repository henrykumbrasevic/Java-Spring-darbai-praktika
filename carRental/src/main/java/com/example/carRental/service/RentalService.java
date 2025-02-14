package com.example.carRental.service;

import com.example.carRental.model.Car;
import com.example.carRental.model.Rental;
import com.example.carRental.repository.CarRepository;
import com.example.carRental.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RentalService {

  private RentalRepository rentalRepository;
  private CarRepository carRepository;

  @Autowired
  public RentalService(RentalRepository rentalRepository) {
    this.rentalRepository = rentalRepository;
  }

  public Rental saveRental(Rental rental) {
    return rentalRepository.save(rental);
  }
}
