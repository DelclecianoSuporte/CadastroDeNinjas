package dev.java10x.CadastroDeNinjas.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/usuarios/**",
                                "/auth/login"
                        ).permitAll()

                        // NINJAS
                        .requestMatchers(HttpMethod.POST, "/ninjas/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/ninjas/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/ninjas/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/ninjas/**")
                        .hasAnyRole("ADMIN", "USER")

                        // MISSÕES
                        .requestMatchers(HttpMethod.POST, "/missoes/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.PUT, "/missoes/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.DELETE, "/missoes/**")
                        .hasRole("ADMIN")

                        .requestMatchers(HttpMethod.GET, "/missoes/**")
                        .hasAnyRole("ADMIN", "USER")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
}
