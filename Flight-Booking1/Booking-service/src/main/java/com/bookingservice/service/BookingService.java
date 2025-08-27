package com.bookingservice.service;

import com.bookingservice.entity.Booking;
import com.bookingservice.payload.BookingResponse;

import java.util.List;

public interface BookingService {
//    Booking bookFlight(Booking booking);
    List<Booking> getAllBookings();
    Booking getBookingById(Long id);
    BookingResponse bookFlight(Booking booking);
    Booking updateBooking(Long id, Booking updatedBooking);
    void deleteBooking(Long id);


}
