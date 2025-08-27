package com.searchservice.service;

import com.searchservice.entities.Flight;

import com.searchservice.repository.FlightRepository;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FlightServiceImplTest {

    @InjectMocks
    private FlightServiceImpl flightService;

    @Mock
    private FlightRepository flightRepository;

    private Flight flight;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        flight = new Flight();
        flight.setId(1L);
        flight.setFlightNumber("AI101");
        flight.setDepartureLocation("PUNE");
        flight.setArrivalLocation("DELHI");
        flight.setDepartureTime(LocalDateTime.of(2025, 4, 20, 10, 0));
        flight.setArrivalTime(LocalDateTime.of(2025, 4, 20, 12, 0));
        flight.setAvailableSeats(150);
    }

    @Test
    void testAddFlight() {
        when(flightRepository.save(any(Flight.class))).thenReturn(flight);

        Flight savedFlight = flightService.addFlight(flight);

        assertNotNull(savedFlight);
        assertEquals("AI101", savedFlight.getFlightNumber());
        verify(flightRepository, times(1)).save(any(Flight.class));
    }

    @Test
    void testGetAllFlights() {
        List<Flight> flights = Collections.singletonList(flight);
        when(flightRepository.findAll()).thenReturn(flights);

        List<Flight> result = flightService.getAllFlights();

        assertEquals(1, result.size());
        assertEquals("PUNE", result.get(0).getDepartureLocation());
        verify(flightRepository, times(1)).findAll();
    }

    @Test
    void testGetFlightById() {
        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));

        Flight result = flightService.getFlightById(1L);

        assertNotNull(result);
        assertEquals("DELHI", result.getArrivalLocation());
        verify(flightRepository, times(1)).findById(1L);
    }

    @Test
    void testGetFlightsByRoute() {
        when(flightRepository.findByDepartureLocationAndArrivalLocation("PUNE", "DELHI"))
                .thenReturn(List.of(flight));

        List<Flight> result = flightService.getFlightsByRoute("PUNE", "DELHI");

        assertEquals(1, result.size());
        assertEquals("DELHI", result.get(0).getArrivalLocation());
        verify(flightRepository, times(1))
                .findByDepartureLocationAndArrivalLocation("PUNE", "DELHI");
    }
}
