package com.realestate.spabuyer.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
//                                .requestMatchers("/admin/**").hasRole("ADMIN")
//                                .requestMatchers("/buyer/**").hasRole("BUYER")
                                .requestMatchers("/public/**").permitAll()
                                .requestMatchers("/api/**").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin(withDefaults())
                .httpBasic(withDefaults());
//                .logout(logout -> logout
//                        .logoutUrl("/logout") // Configure logout URL
//                        .logoutSuccessUrl("/login") // Redirect to login page after logout
//                        .invalidateHttpSession(true) // Invalidate session
//                        .deleteCookies("JSESSIONID") // Delete cookies
//                );

        return http.build();
    }
}


