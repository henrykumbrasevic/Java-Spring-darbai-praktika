package com.example.carRental.controller;

import com.example.carRental.dto.RentalMapper;
import com.example.carRental.dto.RentalRequestDTO;
import com.example.carRental.dto.RentalResponseDTO;
import com.example.carRental.model.Car;
import com.example.carRental.model.Rental;
import com.example.carRental.model.User;
import com.example.carRental.service.CarService;
import com.example.carRental.service.RentalService;
import com.example.carRental.service.UserService;
import jakarta.persistence.Table;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

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
    if (carService.findCarById(rentalRequestDTO.car().getId()).get().getStatus().equals("RENTED")) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Car is already rented");
    }
    User user = ((User) authentication.getPrincipal());
    if (user.getRentals().size() >= 2) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("One user can only rent 2 cars maximum at once");
    }

    Car carFromDb = carService.findCarById(rentalRequestDTO.car().getId()).get();
    carFromDb.setStatus("RENTED");
    carService.saveCar(carFromDb);
    Rental savedRental = rentalService.saveRental(RentalMapper.toRental(rentalRequestDTO, user));

    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedRental.getId()).toUri())
            .body(RentalMapper.toRentalResponseDTO(savedRental, carFromDb));
  }
}
