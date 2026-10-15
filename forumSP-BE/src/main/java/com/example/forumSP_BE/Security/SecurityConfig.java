package com.example.forumSP_BE.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@EnableMethodSecurity
@Configuration
public class SecurityConfig {
    public SecurityConfig() {}

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth

                    // users
                    .requestMatchers(HttpMethod.POST, "/register").permitAll()
                    .requestMatchers(HttpMethod.GET, "/users").fullyAuthenticated()

                    // spaces
                    .requestMatchers(HttpMethod.POST, "/addSpace").fullyAuthenticated()
                    .requestMatchers(HttpMethod.POST, "/{spaceName}/addPost").fullyAuthenticated()
                    .requestMatchers(HttpMethod.GET, "/getAllSpaces").permitAll()
                    .requestMatchers(HttpMethod.GET, "/getAllSpaces/names").permitAll()
                    .requestMatchers(HttpMethod.GET, "/{spaceName}/getPosts").permitAll()
                    .requestMatchers(HttpMethod.GET, "/{spaceName}").permitAll()

                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}