package com.todo.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.session.web.http.CookieHttpSessionIdResolver;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.HttpSessionIdResolver;

@Configuration
public class SecurityConfig {

    @Bean
    public HttpSessionIdResolver httpSessionIdResolver() {
        CookieHttpSessionIdResolver resolver = new CookieHttpSessionIdResolver();
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
        cookieSerializer.setCookiePath("/");
        cookieSerializer.setCookieMaxAge(3600);
        cookieSerializer.setSameSite("None"); // Hali ham saqlab qoldik, lekin cross-site bo‘lmasa kerak emas
        cookieSerializer.setUseSecureCookie(false); // HTTPS bo‘lmasa
        cookieSerializer.setUseHttpOnlyCookie(true);
        resolver.setCookieSerializer(cookieSerializer);
        return resolver;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // CSRF o‘chirilgan
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Sessiya kerak bo‘lganda yaratiladi
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/user/**").permitAll() // /user/** uchun autentifikatsiya kerak emas
                .requestMatchers("/**").permitAll() // Barcha static resurslar (HTML, CSS, JS) uchun ruxsat
                .requestMatchers("/todo/**").authenticated() // /todo/** uchun autentifikatsiya kerak
                .anyRequest().authenticated() // Qolganlari uchun autentifikatsiya
            );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}