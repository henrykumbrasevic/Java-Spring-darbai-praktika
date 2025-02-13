package com.example.carRental.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf(c -> c.disable())
            .httpBasic(Customizer.withDefaults())
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(HttpMethod.GET, "/api/cars").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.GET, "/api/cars/available").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/rentals/my").hasRole("USER")
                    .requestMatchers(HttpMethod.GET, "/api/rentals/history").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/api/cars").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/api/rentals").hasRole("USER")
//                    .requestMatchers(HttpMethod.POST, "/api/rentals/return/{id}").hasRole("USER") // authentication for the owner. To adjust later
//                    .requestMatchers(HttpMethod.PUT, "/api/cars/{id}").hasRole("ADMIN")
//                    .requestMatchers(HttpMethod.DELETE, "/api/cars/{id}").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/users").permitAll() // remove later
                    .requestMatchers(HttpMethod.GET, "/api/users").permitAll() // remove later
                    .anyRequest().authenticated());

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
