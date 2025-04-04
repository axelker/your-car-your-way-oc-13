package com.openclassrooms.ycyw.configuration;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * Custom authentication entry point that handles unauthorized access attempts.
 * <p>
 * This class is responsible for sending an HTTP 401 Unauthorized response
 * when an unauthenticated request is made to a protected resource.
 * </p>
 */
@Component
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {

    /**
     * Handles authentication failure by sending a 401 Unauthorized response.
     *
     * @param request       the HTTP request
     * @param response      the HTTP response
     * @param authException the exception triggered by authentication failure
     * @throws IOException      if an I/O error occurs
     * @throws ServletException if a servlet-related error occurs
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized - Please login");
    }
}