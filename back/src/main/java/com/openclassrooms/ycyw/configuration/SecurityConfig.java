package com.openclassrooms.ycyw.configuration;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import com.openclassrooms.ycyw.configuration.filter.JwtCookieFilter;
import com.openclassrooms.ycyw.service.query.UserQueryService;

/**
 * Security configuration class for managing authentication and authorization settings.
 * <p>
 * This class configures Spring Security, including JWT-based authentication,
 * password encoding, and method security.
 * </p>
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Value("${security.publicUrls}")
    private String[] publicUrls;

    @Value("${jwt.secret}")
    private String jwtSecret;

    private final UserQueryService userDetailsService;

    /**
     * Constructs the security configuration with the provided {@link UserQueryService}.
     *
     * @param userDetailsService the service used for loading user details.
     */
    public SecurityConfig(UserQueryService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * Configures security filters and authentication settings.
     *
     * @param http                    the {@link HttpSecurity} object to configure security settings.
     * @param jwtCookieFilter          the JWT filter for authentication.
     * @param authenticationEntryPoint the custom entry point for unauthorized requests.
     * @return a {@link SecurityFilterChain} defining the security configuration.
     * @throws Exception if an error occurs while configuring security.
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http,JwtCookieFilter jwtCookieFilter,CustomAuthEntryPoint authenticationEntryPoint) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(publicUrls).permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint))
                .addFilterBefore(jwtCookieFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    /**
     * Configures the JWT decoder for verifying JWT tokens.
     *
     * @return a {@link JwtDecoder} for decoding JWT tokens.
     */
    @Bean
    public JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withSecretKey(new SecretKeySpec(this.jwtSecret.getBytes(), "HmacSHA256")).build();
    }

    /**
     * Configures the JWT encoder for signing JWT tokens.
     *
     * @return a {@link JwtEncoder} for encoding JWT tokens.
     */
    @Bean
    public JwtEncoder jwtEncoder() {
        return new NimbusJwtEncoder(new ImmutableSecret<>(this.jwtSecret.getBytes()));
    }

    /**
     * Configures the password encoder using BCrypt.
     *
     * @return a {@link BCryptPasswordEncoder} instance.
     */
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configures the authentication manager with user details and password encoding.
     *
     * @param http the {@link HttpSecurity} object to configure authentication.
     * @return an {@link AuthenticationManager} instance.
     * @throws Exception if an error occurs while building the authentication manager.
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http
                .getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());

        return authenticationManagerBuilder.build();
    }

}
