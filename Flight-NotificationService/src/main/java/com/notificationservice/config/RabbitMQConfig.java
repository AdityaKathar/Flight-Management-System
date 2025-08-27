//package com.notificationservice.config;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.support.converter.DefaultClassMapper;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.notificationservice.dto.NotificationMessage;
//
//@Configuration
//public class RabbitMQConfig {
//
//    public static final String QUEUE = "booking_notifications";
//    public static final String EXCHANGE = "booking_exchange";
//    public static final String ROUTING_KEY = "booking_routingKey";
//
////    @Bean
////    public Queue queue() {
////        return new Queue(QUEUE);
////    }
////
////    @Bean
////    public DirectExchange exchange() {   // changed from TopicExchange to DirectExchange
////        return new DirectExchange(EXCHANGE);
////    }
////
////    @Bean
////    public Binding binding(Queue queue, DirectExchange exchange) {
////        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
////    }
//    @Bean
//    public Queue queue() {
//        return new Queue("booking_notifications", false); // false means durable, won't auto-delete
//    }
//
//    @Bean
//    public TopicExchange exchange() {
//        return new TopicExchange("booking_exchange");
//    }
//
//    @Bean
//    public Binding binding(Queue queue, TopicExchange exchange) {
//        return BindingBuilder
//                .bind(queue)
//                .to(exchange)
//                .with("booking_routingKey");
//    }
//
//    @Bean
//    public Jackson2JsonMessageConverter messageConverter() {
//        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();
//
//        DefaultClassMapper classMapper = new DefaultClassMapper();
//        Map<String, Class<?>> idClassMapping = new HashMap<>();
//        idClassMapping.put("com.bookingservice.payload.NotificationMessage", NotificationMessage.class);
//        classMapper.setIdClassMapping(idClassMapping);
//
//        converter.setClassMapper(classMapper);
//        return converter;
//    }
//
//
//    
//}


package com.notificationservice.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.notificationservice.dto.NotificationMessage;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE = "booking_notifications";
    public static final String EXCHANGE = "booking_exchange";
    public static final String ROUTING_KEY = "booking_routingKey";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE, false);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();

        DefaultClassMapper classMapper = new DefaultClassMapper();
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("com.bookingservice.payload.NotificationMessage", NotificationMessage.class);
        classMapper.setIdClassMapping(idClassMapping);

        converter.setClassMapper(classMapper);
        return converter;
    }
}

