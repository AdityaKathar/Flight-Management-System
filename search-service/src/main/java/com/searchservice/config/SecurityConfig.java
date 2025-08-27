package com.searchservice.config;

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
//                .requestMatchers("/api/auth/**").permitAll()  // for testing, if needed
//                .requestMatchers("/api/flights/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
//                .requestMatchers("/api/flights/{id}").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
//                .requestMatchers("/api/flights").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
//                .requestMatchers("/api/flights/**").hasAuthority("ROLE_ADMIN")
//                .anyRequest().authenticated()
//            )
//            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
//    }
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//            .csrf(csrf -> csrf.disable())
//            .authorizeHttpRequests(auth -> auth
//                .requestMatchers("/api/auth/**").permitAll()
//                .requestMatchers(HttpMethod.GET, "/api/flights/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
//                .requestMatchers(HttpMethod.GET, "/api/flights/getFlight/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
//                .requestMatchers(HttpMethod.POST, "/api/flights/addFlight").hasAuthority("ROLE_ADMIN")
//                .requestMatchers(HttpMethod.PUT, "/api/flights/updateFlight/*", "/api/flights/updateSeats/*").hasAuthority("ROLE_ADMIN")
//                .requestMatchers(HttpMethod.DELETE, "/api/flights/deleteFlight/**").hasAuthority("ROLE_ADMIN")
//                .requestMatchers(HttpMethod.PUT, "/api/flights/updateSeats/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
//                .anyRequest().authenticated())
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
                .requestMatchers(HttpMethod.GET, "/api/flights/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/flights/addFlight").hasAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/flights/updateFlight/**", "/api/flights/updateSeats/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/flights/deleteFlight/**").hasAuthority("ROLE_ADMIN")
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
