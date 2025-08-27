package com.searchservice.service;

import com.searchservice.entities.Flight;
import com.searchservice.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightServiceImpl implements FlightService {

    @Autowired
    private FlightRepository flightRepository;

    @Override
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    @Override
    public List<Flight> getFlightsByRoute(String from, String to) {
        return flightRepository.findByDepartureLocationAndArrivalLocation(from, to);
    }

    @Override
    public Flight addFlight(Flight flight) {
        return flightRepository.save(flight);
    }
    
    @Override
    public Flight getFlightById(Long id) {
        return flightRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Flight not found with id: " + id));
    }

    @Override
    public Flight updateFlight(Long id, Flight flight) {
        Flight existing = getFlightById(id);
        existing.setFlightNumber(flight.getFlightNumber());
        existing.setDepartureLocation(flight.getDepartureLocation());
        existing.setArrivalLocation(flight.getArrivalLocation());
        existing.setDepartureTime(flight.getDepartureTime());
        existing.setArrivalTime(flight.getArrivalTime());
        existing.setAvailableSeats(flight.getAvailableSeats());
        return flightRepository.save(existing);
    }

    @Override
    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }

}
