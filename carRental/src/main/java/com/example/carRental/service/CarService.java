package com.example.carRental.service;

import com.example.carRental.model.Car;
import com.example.carRental.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {

  private CarRepository carRepository;

  @Autowired
  public CarService(CarRepository carRepository) {
    this.carRepository = carRepository;
  }

  public List<Car> findAllCars() {
    return carRepository.findAll();
  }

  public Car savedCar(Car car) {
    return carRepository.save(car);
  }

  public boolean existsCarById(long id) {
    return carRepository.existsById(id);
  }

  public void deleteCarById(long id) {
    carRepository.deleteById(id);
  }
}
