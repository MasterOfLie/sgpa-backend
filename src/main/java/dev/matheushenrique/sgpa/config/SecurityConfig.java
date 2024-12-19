package dev.matheushenrique.sgpa.config;

import dev.matheushenrique.sgpa.config.filter.JwtFilter;
import dev.matheushenrique.sgpa.config.jwt.JwtService;
import dev.matheushenrique.sgpa.service.UsuarioService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    @Bean
    public JwtFilter jwtFilter(JwtService jwtService, UsuarioService usuarioService) {
        return new JwtFilter(jwtService, usuarioService);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtFilter jwtFilter) throws Exception {
       return http
                .csrf(AbstractHttpConfigurer::disable)
               .formLogin(AbstractHttpConfigurer::disable)
               .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequests ->{
                    authorizeRequests.requestMatchers("/api/v1/auth/**").permitAll();
                    authorizeRequests.anyRequest().authenticated();
                        })
               .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
               .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public AuthenticationManager noopAuthenticationManager() {
        return authentication -> {
            throw new AuthenticationServiceException("Authentication is disabled");
        };
    }
}
