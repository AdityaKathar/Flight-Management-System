package com.bookingservice.controller;

import com.bookingservice.entity.Booking;

import com.bookingservice.payload.BookingResponse;
import com.bookingservice.service.BookingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/api/bookings")
public class BookingController {
	
    @Autowired
    private BookingService bookingService;

    @PostMapping("/bookFlight")
    public BookingResponse bookFlight(@RequestBody @Valid Booking booking) {
        return bookingService.bookFlight(booking);
    }

    @GetMapping("/getAllBookings")
    public List<Booking> getAllBookings() {
        return bookingService.getAllBookings();
    }

    @GetMapping("/getBooking/{id}")
    public Booking getBooking(@PathVariable Long id) {
        return bookingService.getBookingById(id);
    }

    @PutMapping("/updateBooking/{id}")
    public Booking updateBooking(@PathVariable Long id, @RequestBody Booking updatedBooking) {
        return bookingService.updateBooking(id, updatedBooking);
    }

    @DeleteMapping("/deleteBooking/{id}")
    public String deleteBooking(@PathVariable Long id) {
        bookingService.deleteBooking(id);
        return "Booking with ID " + id + " deleted successfully.";
    }
}