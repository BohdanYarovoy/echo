package com.echoteam.app.configurations;

import com.echoteam.app.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .userDetailsService(userService)
                .csrf(Customizer.withDefaults())
                .authorizeHttpRequests(request -> request
                        .requestMatchers(
                                "/",
                                "/web/main",
                                "/images/**",
                                "/scripts/**",
                                "/styles/**"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(c -> c.loginPage("/web/main?loginEnabled=true")
                        .failureUrl("/web/main?loginEnabled=true&error=true")
                        .loginProcessingUrl("/login")
                        .permitAll()
                )
                .logout(e -> {
                    e.permitAll();
                    e.logoutUrl("/logout");
                    e.logoutSuccessUrl("/");
                });

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
