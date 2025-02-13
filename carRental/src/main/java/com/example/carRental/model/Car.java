package com.example.carRental.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "cars")
public class Car {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String brand;
  private int year;
  private String status = "AVAILABLE";
  private String model;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "car_id")
  private List<Rental> rentals;


  public Car(String brand, String model, int year, List<Rental> rentals) {
    this.brand = brand;
    this.model = model;
    this.year = year;
    this.rentals = rentals;
  }

  public List<Rental> getRentals() {
    return rentals;
  }

  public void setRentals(List<Rental> rentals) {
    this.rentals = rentals;
  }

  public String getModel() {
    return model;
  }

  public void setModel(String model) {
    this.model = model;
  }

  public Car() {

  }

  public long getId() {
    return id;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
