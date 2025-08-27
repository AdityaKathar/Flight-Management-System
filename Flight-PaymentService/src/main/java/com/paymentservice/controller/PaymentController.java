package com.paymentservice.controller;

import com.paymentservice.payload.PaymentRequest;

import com.razorpay.*;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashMap;
import java.util.Map;


@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

	
	
    @Value("${razorpay.key_id}")
    private String keyId;

    @Value("${razorpay.key_secret}")
    private String keySecret;

    @PostMapping("/createOrder")
    public String createOrder(@RequestBody PaymentRequest paymentRequest) throws RazorpayException {
        RazorpayClient client = new RazorpayClient(keyId, keySecret);

        JSONObject options = new JSONObject();
        options.put("amount", paymentRequest.getAmount());
        options.put("currency", paymentRequest.getCurrency());
        options.put("receipt", paymentRequest.getReceipt());

        Order order = client.orders.create(options);

        return order.toString();
    }
}
