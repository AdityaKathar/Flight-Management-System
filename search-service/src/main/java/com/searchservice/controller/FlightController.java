package com.searchservice.controller;

import com.searchservice.entities.Flight;
import com.searchservice.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/api/flights")
public class FlightController {

	
	
    @Autowired
    private FlightService flightService;

    @GetMapping("/getAllFlights")
    public List<Flight> getAllFlights(@RequestParam(required = false) String from,
                                      @RequestParam(required = false) String to) {
        if (from != null && to != null) {
            return flightService.getFlightsByRoute(from, to);
        }
        return flightService.getAllFlights();
    }

    @PostMapping("/addFlight")
    public Flight addFlight(@RequestBody Flight flight) {
        return flightService.addFlight(flight);
    }

    @GetMapping("/getFlight/{id}")
    public Flight getFlightById(@PathVariable Long id) {
        return flightService.getFlightById(id);
    }

    @PutMapping("/updateFlight/{id}")
    public Flight updateFlight(@PathVariable Long id, @RequestBody Flight flight) {
        return flightService.updateFlight(id, flight);
    }

    @DeleteMapping("/deleteFlight/{id}")
    public void deleteFlight(@PathVariable Long id) {
        flightService.deleteFlight(id);
    }

    @PutMapping("/updateSeats/{id}")
    public ResponseEntity<Flight> updateAvailableSeats(@PathVariable Long id, @RequestParam int seatsToReduce) {
        Flight flight = flightService.getFlightById(id);
        if (flight.getAvailableSeats() < seatsToReduce) {
            return ResponseEntity.badRequest().build();
        }
        flight.setAvailableSeats(flight.getAvailableSeats() - seatsToReduce);
        Flight updated = flightService.updateFlight(id, flight);
        return ResponseEntity.ok(updated);
    }
}