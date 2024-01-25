package com.bartoszj.forumwithmysql.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(c -> c.disable())
                .authorizeHttpRequests((auth) -> {auth
                        .requestMatchers("/user").permitAll()
                        .requestMatchers("/user/sign-up", "/error").permitAll()
                        .requestMatchers("/threads").permitAll()
                        .requestMatchers("/threads/**").hasRole("USER")
                        .requestMatchers("/comments/**").hasRole("USER")
                        .anyRequest().authenticated();
        })
                .userDetailsService(this.userDetailsService)
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
