package com.openclassrooms.ycyw.exception;

/**
 * Exception thrown when a user attempts to perform an unauthorized action.
 * <p>
 * This exception is used to indicate that the user does not have the necessary
 * permissions to execute the requested operation.
 * </p>
 */
public class UnauthorizedActionException extends RuntimeException {

    /**
     * Constructs a new {@code UnauthorizedActionException} with the specified detail message.
     *
     * @param message the detail message explaining the reason for the exception.
     */
    public UnauthorizedActionException(String message) {
        super(message);
    }
}
