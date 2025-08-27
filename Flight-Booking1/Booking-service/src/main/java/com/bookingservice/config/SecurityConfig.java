package com.bookingservice.config;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;



@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//            .csrf(csrf -> csrf.disable())
//            .authorizeHttpRequests(auth -> auth
//                .requestMatchers("/api/auth/**").permitAll()  // allow if you have any auth endpoints
//                .requestMatchers("/api/bookings/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
//                .requestMatchers("/api/bookings/{id}").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
//                .requestMatchers("/api/bookings/**").hasAuthority("ROLE_ADMIN")
//                .anyRequest().authenticated()
//            )
//            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/bookings/getAllBookings", "/api/bookings/getBooking/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
//                .requestMatchers(HttpMethod.GET, "/api/bookings/getAllBookings", "/api/bookings/getBooking/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/bookings/bookFlight").hasAuthority("ROLE_USER")
                .requestMatchers(HttpMethod.PUT, "/api/bookings/updateBooking/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/bookings/deleteBooking/**").hasAuthority("ROLE_ADMIN")
                .anyRequest().authenticated())
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}