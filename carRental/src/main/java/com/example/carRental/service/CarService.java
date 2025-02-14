package com.example.carRental.service;

import com.example.carRental.model.Car;
import com.example.carRental.model.Rental;
import com.example.carRental.repository.CarRepository;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

  private CarRepository carRepository;
  private Car car;

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

  public Car findById(long id) {
    return carRepository.findById(id).get();
  }

  public Car findCarById(long id) {
    return carRepository.findById(id).get();
  }

  public Car saveCar(Car car) {
    return carRepository.save(car);
  }
}
