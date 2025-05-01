package com.jwt_revision.Test_jwt_methods.configuration;


import com.jwt_revision.Test_jwt_methods.filter.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;

    // ✅ New: Define security filter chain
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/v1/user/register",
                                "/v1/user/login",
                                "/v1/user/forget-password/verify-email/**",
                                "/v1/user/verify-otp/**",
                                "/v1/user/set-new-password/**",
                                "/location/update",
                                "pdf/create-pdf",
                                "pdf/find-replace-text",
                                "pdf/replace-image"
                        ).permitAll()
                        .requestMatchers("/excel-operation/dump-excel-data").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // ✅ Add this line
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
