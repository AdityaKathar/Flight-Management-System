package com.bookingservice.entity;

import jakarta.persistence.*;

import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long flightId;
    
    @NotBlank(message = "Customer name is required")
    private String customerName;
    
    @Min(value = 0, message = "Adults must be >= 0")
    private int numberOfAdults;
    
    @Min(value = 0, message = "Adults must be >= 0")
    private int numberOfChildren;
    
    private double totalFare;
    private LocalDateTime bookingTime;

    // --- Getters and Setters ---

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFlightId() {
        return flightId;
    }

    public void setFlightId(Long flightId) {
        this.flightId = flightId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public int getNumberOfAdults() {
        return numberOfAdults;
    }

    public void setNumberOfAdults(int numberOfAdults) {
        this.numberOfAdults = numberOfAdults;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    public void setNumberOfChildren(int numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
    }

    public double getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(double totalFare) {
        this.totalFare = totalFare;
    }

    public LocalDateTime getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(LocalDateTime bookingTime) {
        this.bookingTime = bookingTime;
    }

    // --- Optional: toString (for logging/debugging) ---
    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", flightId=" + flightId +
                ", customerName='" + customerName + '\'' +
                ", numberOfAdults=" + numberOfAdults +
                ", numberOfChildren=" + numberOfChildren +
                ", totalFare=" + totalFare +
                ", bookingTime=" + bookingTime +
                '}';
    }
}
