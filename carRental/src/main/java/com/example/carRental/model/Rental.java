package com.example.carRental.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "rental")
public class Rental {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private long userId;
  private long carId;
  private LocalDate rentalStart;
  private LocalDate rentalEnd;
  private BigDecimal price;

  public Rental(long id, long userId, long carId, LocalDate rentalEnd, LocalDate rentalStart, BigDecimal price) {
    this.userId = userId;
    this.carId = carId;
    this.rentalEnd = rentalEnd;
    this.rentalStart = rentalStart;
    this.price = price;
  }

  public Rental() {

  }

  public long getId() {
    return id;
  }

  public long getCarId() {
    return carId;
  }

  public void setCarId(long carId) {
    this.carId = carId;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public LocalDate getRentalStart() {
    return rentalStart;
  }

  public void setRentalStart(LocalDate rentalStart) {
    this.rentalStart = rentalStart;
  }

  public LocalDate getRentalEnd() {
    return rentalEnd;
  }

  public void setRentalEnd(LocalDate rentalEnd) {
    this.rentalEnd = rentalEnd;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }
}
