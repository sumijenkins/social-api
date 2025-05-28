package com.example.social_api.config;

import com.example.social_api.repository.UserRepository;
import com.example.social_api.security.ApiKeyFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/*
controls every http request if x-api-key is valid
 */



@Configuration
public class SecurityConfig {

    private final UserRepository userRepository;

    public SecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Bean
    public ApiKeyFilter apiKeyFilter() {
        return new ApiKeyFilter(userRepository);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users").permitAll()        // kullanıcı oluşturma açık
                        .requestMatchers("/api/users/me").authenticated() // kişisel veri korumalı
                        .requestMatchers("/api/comments/**").authenticated() // yorum ekleme, düzenleme, silme korumalı
                        .anyRequest().permitAll()
                )
                .addFilterBefore(apiKeyFilter(), UsernamePasswordAuthenticationFilter.class); // ÖNEMLİ!

        return http.build();
    }
}


