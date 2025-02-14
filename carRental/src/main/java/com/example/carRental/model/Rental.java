package com.example.carRental.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "rentals")
public class Rental {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  private User user;

  @ManyToOne(fetch = FetchType.EAGER)
  private Car car;


  private LocalDateTime rentalStart;
  private LocalDateTime rentalEnd;
  private BigDecimal price;

  public Rental(User user, Car car, LocalDateTime rentalEnd, LocalDateTime rentalStart, BigDecimal price) {
    this.user = user;
    this.car = car;
    this.rentalStart = rentalStart;
    this.rentalEnd = rentalEnd;
    this.price = price;
  }

  public Rental() {

  }

  public long getId() {
    return id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Car getCar() {
    return car;
  }

  public void setCar(Car car) {
    this.car = car;
  }

  public LocalDateTime getRentalStart() {
    return rentalStart;
  }

  public void setRentalStart(LocalDateTime rentalStart) {
    this.rentalStart = rentalStart;
  }

  public LocalDateTime getRentalEnd() {
    return rentalEnd;
  }

  public void setRentalEnd(LocalDateTime rentalEnd) {
    this.rentalEnd = rentalEnd;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }
}
