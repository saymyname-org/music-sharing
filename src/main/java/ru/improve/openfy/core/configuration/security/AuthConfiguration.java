package ru.improve.openfy.core.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import ru.improve.openfy.api.filter.AuthFilter;
import ru.improve.openfy.core.security.AuthService;

@Configuration
public class AuthConfiguration {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

//    @Bean
//    public GrpcAuthenticationReader grpcAuthenticationReader() {
//        return null;
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthService authService) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        auth -> auth
                                .anyRequest().authenticated()
                )
                .oauth2ResourceServer(conf -> conf.jwt(Customizer.withDefaults()))
                .addFilterAfter(new AuthFilter(authService), BearerTokenAuthenticationFilter.class);

        return http.build();
    }

}
