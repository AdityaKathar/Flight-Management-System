package com.searchservice.service;

import com.searchservice.entities.Flight;
import java.util.List;

public interface FlightService {
    List<Flight> getAllFlights();
    List<Flight> getFlightsByRoute(String from, String to);
    Flight addFlight(Flight flight);
    Flight getFlightById(Long id);
    Flight updateFlight(Long id, Flight flight);
    void deleteFlight(Long id);

}
