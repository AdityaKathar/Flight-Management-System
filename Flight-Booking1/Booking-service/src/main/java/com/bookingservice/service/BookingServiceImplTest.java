package com.bookingservice.service;

import com.bookingservice.entity.Booking;
import com.bookingservice.payload.BookingResponse;
import com.bookingservice.repository.BookingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceImplTest {

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Mock
    private BookingRepository bookingRepository;

    private Booking booking;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        booking = new Booking();
        booking.setId(1L);
        booking.setFlightId(5L);
        booking.setCustomerName("Ashlesh");
        booking.setNumberOfAdults(2);
        booking.setNumberOfChildren(1);
        booking.setBookingTime(LocalDateTime.now());
        booking.setTotalFare(0.0);  // Calculated later
    }

    @Test
    void testBookFlight() {
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        BookingResponse saved = bookingService.bookFlight(booking);

        assertNotNull(saved);
        assertEquals("Ashlesh", saved.getCustomerName());
        verify(bookingRepository, times(1)).save(any(Booking.class));
    }

    @Test
    void testGetAllBookings() {
        when(bookingRepository.findAll()).thenReturn(List.of(booking));

        List<Booking> bookings = bookingService.getAllBookings();

        assertEquals(1, bookings.size());
        verify(bookingRepository, times(1)).findAll();
    }

    @Test
    void testGetBookingById() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        Booking result = bookingService.getBookingById(1L);

        assertNotNull(result);
        assertEquals(5L, result.getFlightId());
        verify(bookingRepository, times(1)).findById(1L);
    }
}
