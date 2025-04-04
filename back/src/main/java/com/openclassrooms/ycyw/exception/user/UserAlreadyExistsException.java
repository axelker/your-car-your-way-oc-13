package com.openclassrooms.ycyw.exception.user;

/**
 * Exception thrown when attempting to create a user that already exists.
 * <p>
 * This exception is used to indicate that a user with the given credentials
 * (e.g., email or username) is already present in the system.
 * </p>
 */
public class UserAlreadyExistsException extends RuntimeException {

    /**
     * Constructs a new {@code UserAlreadyExistsException} with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception.
     */
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}
