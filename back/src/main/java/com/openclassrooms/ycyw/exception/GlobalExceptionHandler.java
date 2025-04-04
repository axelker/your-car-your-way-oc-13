package com.openclassrooms.ycyw.exception;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.openclassrooms.ycyw.dto.response.ErrorResponse;
import com.openclassrooms.ycyw.exception.user.UserAlreadyExistsException;

/**
 * Global exception handler for managing API exceptions.
 * <p>
 * This class provides centralized exception handling for REST controllers.
 * It captures specific exceptions and returns appropriate HTTP responses
 * with structured error messages.
 * </p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles cases where a requested element is not found.
     *
     * @param ex      the exception thrown when an element is not found.
     * @param request the web request where the exception occurred.
     * @return a {@link ResponseEntity} with a 404 NOT FOUND status and an error response.
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(NoSuchElementException ex, WebRequest request) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND, request);
    }

    /**
     * Handles cases where a user already exists during registration.
     *
     * @param ex      the exception thrown when a user already exists.
     * @param request the web request where the exception occurred.
     * @return a {@link ResponseEntity} with a 409 CONFLICT status and an error response.
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExists(UserAlreadyExistsException ex, WebRequest request) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT, request);
    }

    /**
     * Handles authentication failures due to invalid credentials.
     *
     * @param ex      the exception thrown when credentials are invalid.
     * @param request the web request where the exception occurred.
     * @return a {@link ResponseEntity} with a 401 UNAUTHORIZED status and an error response.
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex, WebRequest request) {
        return buildErrorResponse("Invalid email or password.", HttpStatus.UNAUTHORIZED, request);
    }

    /**
     * Handles cases where an action is unauthorized.
     *
     * @param ex      the exception thrown when an action is forbidden.
     * @param request the web request where the exception occurred.
     * @return a {@link ResponseEntity} with a 403 FORBIDDEN status and an error response.
     */
    @ExceptionHandler(UnauthorizedActionException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedAction(UnauthorizedActionException ex, WebRequest request) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.FORBIDDEN, request);
    }

    /**
     * Handles cases where a bad request is made (e.g., invalid arguments).
     *
     * @param ex      the exception thrown when a request is malformed.
     * @param request the web request where the exception occurred.
     * @return a {@link ResponseEntity} with a 400 BAD REQUEST status and an error response.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestFound(IllegalArgumentException ex, WebRequest request) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, request);
    }

    /**
     * Handles unexpected exceptions that are not specifically handled.
     *
     * @param ex      the exception thrown.
     * @param request the web request where the exception occurred.
     * @return a {@link ResponseEntity} with a 500 INTERNAL SERVER ERROR status and an error response.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, WebRequest request) {
        return buildErrorResponse("An unexpected error occurred: " + ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    /**
     * Builds a standardized error response.
     *
     * @param message the error message.
     * @param status  the HTTP status code.
     * @param request the web request where the error occurred.
     * @return a {@link ResponseEntity} containing the error response.
     */
    private ResponseEntity<ErrorResponse> buildErrorResponse(String message, HttpStatus status, WebRequest request) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .message(message)
                .path(request.getDescription(false).replace("uri=", ""))
                .build();

        return ResponseEntity.status(status).body(errorResponse);
    }
}
