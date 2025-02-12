package com.example.carRental.dto;

import com.example.carRental.model.Car;

import java.util.List;

public class CarMapper {

  public static List<CarResponseDTO> toCarResponseDTOList(List<Car> cars) {
    List<CarResponseDTO> result = cars.stream().map(car -> new CarResponseDTO(car.getId(), car.getBrand(), car.getModel(), car.getYear(), car.getStatus())).toList();
    return result;
  }

  public static Car toCar(CarRequestDTO carRequestDTO) {
    Car car = new Car(carRequestDTO.brand(), carRequestDTO.model(), carRequestDTO.year(), List.of());
    return car;
  }

  public static CarResponseDTO toCarResponseDTO(Car car) {
    return new CarResponseDTO(car.getId(), car.getBrand(), car.getModel(), car.getYear(), car.getStatus());
  }
}
