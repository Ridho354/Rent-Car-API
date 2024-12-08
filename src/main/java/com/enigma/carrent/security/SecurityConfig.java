package com.enigma.carrent.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // annotation ini ada untuk mengamankan method2 spesifik di aplikasi kita
// nah ini memungkinkan kita untuk menggunakan anonnotation2 security lainnya
// misal @PreAuthorize di method-method kita (lebih baik awal masuk/entry point, di controller)
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers("api/rentcar/auth/login").permitAll()
                                .requestMatchers("api/rentcar/auth/register").permitAll()
                                .requestMatchers("api/rentcar/auth/register-super-admin").permitAll()
                                .requestMatchers("swagger-ui/**").permitAll()
                                .requestMatchers("v3/api-docs/**").permitAll()
                                .anyRequest().authenticated()
//                                .requestMatchers(HttpMethod.POST, "/api/v1/auth/register-admin").hasAnyRole(String.valueOf(UserRole.SUPER_ADMIN))
//                                .requestMatchers(HttpMethod.POST, "/api/v1/auth/register-admin").hasAnyRole(String.valueOf("SUPER_ADMIN"))
//                                .requestMatchers("/api/v1/customers/**").hasAnyRole("ADMIN", "SUPER_ADMIN")
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
