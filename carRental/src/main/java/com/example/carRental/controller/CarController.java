package com.example.carRental.controller;

import com.example.carRental.dto.CarMapper;
import com.example.carRental.dto.CarRequestDTO;
import com.example.carRental.dto.CarResponseDTO;
import com.example.carRental.model.Car;
import com.example.carRental.service.CarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CarController {

  private final CarService carService;

  @Autowired
  public CarController(CarService carService) {
    this.carService = carService;
  }

  @GetMapping("/cars")
  public ResponseEntity<List<CarResponseDTO>> getCars() {
    return ResponseEntity.ok(CarMapper.toCarResponseDTOList(carService.findAllCars()));
  }

  @GetMapping("/cars/available")
  public ResponseEntity<List<CarResponseDTO>> getCarsAvailable() {
    List<Car> carsAvailable = carService.findAllCars().stream().filter(car -> car.getStatus().equals("AVAILABLE")).toList();
    return ResponseEntity.ok(CarMapper.toCarResponseDTOList(carsAvailable));
  }

  @PostMapping("/cars")
  public ResponseEntity<?> addCar(@RequestBody CarRequestDTO carRequestDTO) {
    
    Car savedCar = carService.savedCar(CarMapper.toCar(carRequestDTO));

    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedCar.getId()).toUri()).body(CarMapper.toCarResponseDTO(savedCar));
  }

  @PutMapping("/cars/{id}")
  public ResponseEntity<?> updateCar(@PathVariable long id, @RequestBody CarRequestDTO carRequestDTO) {

    if (carService.existsCarById(id)) {

      Car carFromDb = carService.findCarById(id).get();

      CarMapper.updateCarFromDTO(carRequestDTO, carFromDb);
      carService.saveCar(carFromDb);

      return ResponseEntity.ok(carRequestDTO);
    }

    Car savedCar = carService.savedCar(CarMapper.toCar(carRequestDTO));

    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                    .replacePath("/api/cars/{id}")
                    .buildAndExpand(savedCar.getId())
                    .toUri())
            .body(savedCar);
  }

  @PutMapping("/cars/{id}/rent")
  public ResponseEntity<?> changeCarStatus(@PathVariable long id) {

    if (!carService.existsCarById(id)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("car was not found");
    }
    Car carFromDb = carService.findCarById(id).get();
    carFromDb.setStatus(carFromDb.getStatus().equals("AVAILABLE") ? "RENTED" : "AVAILABLE");
    carService.saveCar(carFromDb);


    return ResponseEntity.ok(CarMapper.toCarResponseDTO(carFromDb));
  }


  @DeleteMapping("/cars/{id}")
  public ResponseEntity<?> deleteCar(@PathVariable long id) {

    if (!carService.existsCarById(id)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Car not found.");
    }

    if (carService.findById(id).getStatus().equals("RENTED")) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Car is rented. Can't remove that one");
    }
    carService.deleteCarById(id);
    return ResponseEntity.noContent().build();
  }
}
