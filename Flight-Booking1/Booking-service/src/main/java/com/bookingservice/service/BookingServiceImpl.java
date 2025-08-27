package com.bookingservice.service;

//import com.bookingservice.config.RabbitMQConfig;

import com.bookingservice.entity.Booking;
import com.bookingservice.feign.FlightClient;
import com.bookingservice.feign.PaymentClient;
import com.bookingservice.payload.BookingResponse;
import com.bookingservice.payload.Flight;
import com.bookingservice.payload.PaymentRequest;
import com.bookingservice.repository.BookingRepository;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private FlightClient flightClient; 
    @Autowired
    private PaymentClient paymentClient;
    
    @Autowired
    private RabbitTemplate rabbitTemplate;

   


//    @Override
//    public Booking bookFlight(Booking booking) {
//       
//        Flight flight = flightClient.getFlightById(booking.getFlightId());
//
//        if (flight == null) {
//            throw new RuntimeException("Flight not found with ID: " + booking.getFlightId());
//        }
//
//     
//        double farePerAdult = 1000.0;
//        double farePerChild = 500.0;
//
//        double totalFare = booking.getNumberOfAdults() * farePerAdult +
//                           booking.getNumberOfChildren() * farePerChild;
//
//        booking.setTotalFare(totalFare);
//        booking.setBookingTime(LocalDateTime.now());
//
//        return bookingRepository.save(booking);
//    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking getBookingById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }
    @Override
    public BookingResponse bookFlight(Booking booking) {
        Flight flight = flightClient.getFlightById(booking.getFlightId());

        if (flight == null) {
            throw new RuntimeException("Flight not found with ID: " + booking.getFlightId());
        }

        int seatsRequested = booking.getNumberOfAdults() + booking.getNumberOfChildren();

        if (flight.getAvailableSeats() < seatsRequested) {
            throw new RuntimeException("Not enough available seats for this flight.");
        }

        double farePerAdult = 1000.0;
        double farePerChild = 500.0;
        double totalFare = booking.getNumberOfAdults() * farePerAdult +
                           booking.getNumberOfChildren() * farePerChild;

        booking.setTotalFare(totalFare);
        booking.setBookingTime(LocalDateTime.now());

        Booking savedBooking = bookingRepository.save(booking);

       
        flightClient.updateAvailableSeats(booking.getFlightId(), seatsRequested);

        
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setAmount((int)(totalFare * 100));  // Razorpay expects paise
        paymentRequest.setCurrency("INR");
        paymentRequest.setReceipt("booking_receipt_" + savedBooking.getId());

 
        String paymentResponse = paymentClient.createOrder(paymentRequest);

       
        System.out.println("Payment initiated: " + paymentResponse);
        
        String notificationMessage = "Booking confirmed for " + booking.getCustomerName() +
                " | Flight ID: " + booking.getFlightId() +
                " | Total fare: ₹" + booking.getTotalFare();

        //rabbitTemplate.convertAndSend(RabbitMQConfig.NOTIFICATION_QUEUE, notificationMessage);

        BookingResponse response = new BookingResponse();
        response.setBookingId(savedBooking.getId());
        response.setCustomerName(savedBooking.getCustomerName());
        response.setNumberOfAdults(savedBooking.getNumberOfAdults());
        response.setNumberOfChildren(savedBooking.getNumberOfChildren());
        response.setTotalFare(savedBooking.getTotalFare());
        response.setBookingTime(savedBooking.getBookingTime());
        response.setDepartureLocation(flight.getDepartureLocation());
        response.setArrivalLocation(flight.getArrivalLocation());

        return response;
    }

    
    @Override
    public Booking updateBooking(Long id, Booking updatedBooking) {
        Booking existing = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        existing.setCustomerName(updatedBooking.getCustomerName());
        existing.setNumberOfAdults(updatedBooking.getNumberOfAdults());
        existing.setNumberOfChildren(updatedBooking.getNumberOfChildren());
        existing.setFlightId(updatedBooking.getFlightId());

     
        double farePerAdult = 1000.0;
        double farePerChild = 500.0;
        double totalFare = updatedBooking.getNumberOfAdults() * farePerAdult +
                           updatedBooking.getNumberOfChildren() * farePerChild;
        existing.setTotalFare(totalFare);

        return bookingRepository.save(existing);
    }

    @Override
    public void deleteBooking(Long id) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        bookingRepository.delete(booking);
    }


}