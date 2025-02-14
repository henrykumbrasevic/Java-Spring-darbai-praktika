package com.example.carRental.controller;

import com.example.carRental.dto.*;
import com.example.carRental.model.Car;
import com.example.carRental.model.Rental;
import com.example.carRental.model.User;
import com.example.carRental.service.CarService;
import com.example.carRental.service.RentalService;
import com.example.carRental.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class RentalController {

  private final RentalService rentalService;
  private CarService carService;
  private UserService userService;

  @Autowired
  public RentalController(RentalService rentalService, CarService carService, UserService userService) {
    this.rentalService = rentalService;
    this.carService = carService;
    this.userService = userService;
  }

  @PostMapping("/rentals")
  public ResponseEntity<?> addRental(@RequestBody RentalRequestDTO rentalRequestDTO, Authentication authentication) {
    if (carService.findCarById(rentalRequestDTO.car().getId()).getStatus().equals("RENTED")) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Car is already rented");
    }
    User user = ((User) authentication.getPrincipal());
    if (user.getRentals().size() >= 2) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("One user can only rent 2 cars maximum at once");
    }

    Car carFromDb = carService.findCarById(rentalRequestDTO.car().getId());
    carFromDb.setStatus("RENTED");
    carService.saveCar(carFromDb);
    Rental savedRental = rentalService.saveRental(RentalMapper.toRental(rentalRequestDTO, user));

    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedRental.getId()).toUri())
            .body(RentalMapper.toRentalResponseDTO(savedRental, carFromDb));
  }

  @PostMapping("/rentals/return/{rentalId}")
  public ResponseEntity<?> returnRental(@PathVariable long rentalId) {

    if (!rentalService.existsRentalById(rentalId)) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("not found");
    }
    Rental rentalFromDb = rentalService.findRentalById(rentalId);
    Car carFromDb = carService.findCarById(rentalFromDb.getCar().getId());

    carFromDb.setStatus("AVAILABLE");
    carService.saveCar(carFromDb);

    return ResponseEntity.status(HttpStatus.OK).body("Car successfully returned");

  }
}
