package com.tallerwebi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
public class SecurityConfig{

   @Bean
   public BCryptPasswordEncoder passwordEncoder(){
      System.out.println("Creating BCryptPasswordEncoder bean");
         return new BCryptPasswordEncoder();
   }
}
