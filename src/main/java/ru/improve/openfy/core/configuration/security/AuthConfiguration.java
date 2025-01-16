package ru.improve.openfy.core.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import ru.improve.openfy.api.filter.AuthFilter;
import ru.improve.openfy.core.security.AuthService;
import ru.improve.openfy.core.security.CustomAuthorizationEntryPoint;

@Configuration
@EnableWebSecurity
public class AuthConfiguration {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthService authService,
            CustomAuthorizationEntryPoint customAuthorizationEntryPoint) throws Exception {

        return http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        auth -> auth
                                .anyRequest().authenticated()
                )
                .addFilterBefore(new AuthFilter(authService), AuthorizationFilter.class)
                .exceptionHandling(
                        c -> c.authenticationEntryPoint(customAuthorizationEntryPoint)
                )
                .build();
    }

}
