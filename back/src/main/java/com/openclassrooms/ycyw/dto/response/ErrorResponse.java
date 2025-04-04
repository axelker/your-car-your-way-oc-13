package com.openclassrooms.ycyw.dto.response;

import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

/**
 * DTO representing a standardized error response.
 * <p>
 * This class is used to structure error messages returned by the API.
 * It includes useful details such as the timestamp, HTTP status code,
 * error message, and the requested path.
 * </p>
 */
@Getter
@Builder
@Schema(description = "Standard error response structure")
public class ErrorResponse {

    /**
     * The timestamp when the error occurred.
     */
    @Schema(description = "Timestamp when the error occurred", example = "2021-01-01T23:30:00")
    private final LocalDateTime timestamp;

    /**
     * The HTTP status code associated with the error.
     */
    @Schema(description = "HTTP status code", example = "500")
    private final int status;

    /**
     * A message describing the error.
     */
    @Schema(description = "Error message", example = "Invalid request parameters")
    private final String message;

    /**
     * The path of the requested endpoint that triggered the error.
     */
    @Schema(description = "Path of the requested endpoint", example = "/api/5")
    private final String path;
}