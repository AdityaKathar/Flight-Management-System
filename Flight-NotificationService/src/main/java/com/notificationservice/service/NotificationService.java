package com.notificationservice.service;

import com.notificationservice.config.RabbitMQConfig;
import com.notificationservice.dto.NotificationMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class NotificationService {

    @Autowired
    private JavaMailSender mailSender;

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void receiveNotification(NotificationMessage notificationMessage) {
        System.out.println("Notification received for: " + notificationMessage.getCustomerName());
        System.out.println("Message: " + notificationMessage.getMessage());

        // Send email
        sendEmail(notificationMessage);
    }

    private void sendEmail(NotificationMessage message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("ashleshrathod260945@gmail.com");
//        mailMessage.setTo(message.getEmail());   // dynamic recipient email
        mailMessage.setTo("baka.sencho.4510@gmail.com");
        mailMessage.setSubject("Flight Booking Confirmation");
        mailMessage.setText(message.getMessage());

        mailSender.send(mailMessage);

        System.out.println("Email sent successfully to " + message.getEmail());
    }
}
