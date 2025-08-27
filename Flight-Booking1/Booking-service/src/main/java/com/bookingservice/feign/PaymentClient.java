package com.bookingservice.feign;

import com.bookingservice.payload.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.bookingservice.config.FeignClientConfig;

@FeignClient(name = "payment-service", configuration = FeignClientConfig.class)
public interface PaymentClient {

    @PostMapping("/api/payments/createOrder")
    String createOrder(@RequestBody PaymentRequest paymentRequest);
}