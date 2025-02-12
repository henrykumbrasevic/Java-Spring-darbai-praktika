package com.example.carRental.controller;

import com.example.carRental.dto.CarMapper;
import com.example.carRental.dto.CarRequestDTO;
import com.example.carRental.dto.CarResponseDTO;
import com.example.carRental.model.Car;
import com.example.carRental.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CarController {

  private final CarService carService;
  Car car = new Car();
  List<Car> cars = new ArrayList<>();

  @Autowired
  public CarController(CarService carService) {
    this.carService = carService;
  }

  @GetMapping("/cars")
  public ResponseEntity<List<CarResponseDTO>> getCars() {
    return ResponseEntity.ok(CarMapper.toCarResponseDTOList(carService.findAllCars()));
  }

//  @GetMapping("/cars/available")
//  public ResponseEntity<List<CarResponseDTO>> getCarsAvailable() {
//    if ()
//  }

  @PostMapping("/cars")
  public ResponseEntity<?> addCar(@RequestBody CarRequestDTO carRequestDTO) {

    Car savedCar = carService.savedCar(CarMapper.toCar(carRequestDTO));

    return ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedCar.getId()).toUri()).body(CarMapper.toCarResponseDTO(savedCar));
  }

  @DeleteMapping("/cars/{id}")
  public ResponseEntity<Void> deleteCar(@PathVariable long id) {
    if (!carService.existsCarById(id)) {
      return ResponseEntity.notFound().build();
    }
    carService.deleteCarById(id);
    return ResponseEntity.noContent().build();
  }
}
