package com.bookingservice.feign;

import com.bookingservice.payload.Flight;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.bookingservice.config.FeignClientConfig;

@FeignClient(name = "flight-service", configuration = FeignClientConfig.class)
public interface FlightClient {

    // Corrected path
    @GetMapping("/api/flights/getFlight/{id}")
    Flight getFlightById(@PathVariable Long id);

    @PutMapping("/api/flights/updateSeats/{id}")
    void updateAvailableSeats(@PathVariable("id") Long id, @RequestParam("seatsToReduce") int seatsToReduce);
}

//@FeignClient(name = "flight-service", configuration = FeignClientConfig.class)
//public interface FlightClient {
//
//    // ✅ Updated mapping to match FlightController
//    @GetMapping("/api/flights/getFlight/{id}")
//    Flight getFlightById(@PathVariable Long id);
//
//    // ✅ Updated mapping to match FlightController
//    @PutMapping("/api/flights/updateSeats/{id}")
//    void updateAvailableSeats(@PathVariable("id") Long id, @RequestParam("seatsToReduce") int seatsToReduce);
//}







//package com.bookingservice.feign;
//
//
//import com.bookingservice.payload.Flight;
//
//
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@FeignClient(name = "flight-service")
//public interface FlightClient {
//
//    @GetMapping("/api/flights/{id}")
//    Flight getFlightById(@PathVariable Long id);
//    
//    @PutMapping("/api/flights/{id}/seats")
//    void updateAvailableSeats(@PathVariable("id") Long id, @RequestParam("seatsToReduce") int seatsToReduce);
//
//}