package com.jwt_revision.Test_jwt_methods.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

@Component
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // ✅ New: Define security filter chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // disable CSRF for APIs
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/v1/user/register",
                                "/v1/user/login",
                                "/v1/user/forget-password/verify-email/**",
                                "/v1/user/verify-otp/**",
                                "/v1/user/set-new-password/**",
                                "/location/update"
                        ).permitAll() // allow unauthenticated access to these
                        .anyRequest().authenticated() // secure everything else
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // optional: for JWT stateless APIs
                .httpBasic(Customizer.withDefaults()); // basic auth for now (replace with JWT later if needed)

        return http.build();
    }
}
