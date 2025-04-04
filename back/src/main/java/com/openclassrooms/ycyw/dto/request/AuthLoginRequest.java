package com.openclassrooms.ycyw.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

/**
 * DTO representing a login request.
 * <p>
 * This class is used to transfer authentication data from client requests.
 * It includes validation annotations to ensure required fields are provided.
 * </p>
 */
@Getter
@Builder
public class AuthLoginRequest {

    /**
     * The identifier used for login.
     * <p>
     * This can be either an email or a username.
     * </p>
     * <p>
     * This field is required.
     * </p>
     */
    @NotNull(message = "Email is required.")
    private String identifier;
    /**
     * The password associated with the user account.
     * <p>
     * This field is required.
     * </p>
     */
    @NotNull(message = "password is required")
    private String password;
}