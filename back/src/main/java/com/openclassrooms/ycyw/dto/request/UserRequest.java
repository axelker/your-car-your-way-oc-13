package com.openclassrooms.ycyw.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

/**
 * DTO representing a user creation or update request.
 * <p>
 * This class is used to transfer user data from client requests.
 * It includes validation annotations to ensure data integrity.
 * </p>
 */
@Getter
@Builder
public class UserRequest {

    /**
     * The email address of the user.
     * <p>
     * This field is required and must be a valid email format.
     * </p>
     */
    @NotNull(message = "Email is required.")
    @Email(message = "Invalid email format.")
    private String email;

    /**
     * The password for the user account.
     * <p>
     * The password must meet the following requirements:
     * <ul>
     *     <li>Minimum length of 8 characters</li>
     *     <li>At least one digit</li>
     *     <li>At least one lowercase letter</li>
     *     <li>At least one uppercase letter</li>
     *     <li>At least one special character (@#$%^&+=!)</li>
     * </ul>
     * </p>
     */
    @Size(min = 8, message = "Password must be at least 8 characters long.")
    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$",
        message = "Password must contain at least one digit, one lowercase letter, one uppercase letter, and one special character."
    )
    private String password;
}
