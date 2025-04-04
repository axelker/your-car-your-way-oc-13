package com.openclassrooms.ycyw.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.ycyw.dto.request.AuthLoginRequest;
import com.openclassrooms.ycyw.dto.request.UserRequest;
import com.openclassrooms.ycyw.dto.response.ErrorResponse;
import com.openclassrooms.ycyw.dto.response.Response;
import com.openclassrooms.ycyw.dto.response.UserResponse;
import com.openclassrooms.ycyw.service.auth.AuthenticationService;
import com.openclassrooms.ycyw.service.query.UserQueryService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * REST controller for user authentication and account management.
 * <p>
 * This controller provides endpoints for user registration, login, logout,
 * and profile management. Authentication is handled via JWT tokens stored in
 * cookies.
 * </p>
 */
@Tag(name = "Authentication", description = "User authentication and registration")
@ApiResponses({
                @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class)))
})
@RestController
@RequestMapping("/auth")
public class AuthRestController {
        private final AuthenticationService authService;
        private final UserQueryService userQueryService;

        /**
         * Constructs an instance of {@code AuthRestController}.
         *
         * @param authService        the authentication service handling login, logout,
         *                           and registration.
         * @param userQueryService   the service for retrieving user data.
         * @param userCommandService the service for updating user data.
         */
        AuthRestController(AuthenticationService authService, UserQueryService userQueryService) {
                this.authService = authService;
                this.userQueryService = userQueryService;
        }

        /**
         * Retrieves the authenticated user's information.
         *
         * @return the authenticated user's details wrapped in a {@link ResponseEntity}.
         */
        @Operation(summary = "Find info of current user authenticate", description = "Retrieve details of a user using its authentication information.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "User info found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserResponse.class))),
                        @ApiResponse(responseCode = "401", description = "Unauthorized - Token missing or invalid", content = @Content),
                        @ApiResponse(responseCode = "404", description = "User not found", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),

        })
        @GetMapping("me")
        public ResponseEntity<UserResponse> getMe() {
                Long userId = authService.getAuthenticatedUserId();
                return ResponseEntity.ok(userQueryService.findById(userId));
        }


        /**
         * Authenticates a user and issues a JWT token.
         *
         * @param body the login request containing email and password.
         * @return a response containing a success message with the JWT token stored in
         *         a HTTP cookie.
         */
        @Operation(summary = "User Login", description = "Authenticate user and return a JWT token.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Login successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class))),
                        @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
        })
        @PostMapping("login")
        public ResponseEntity<Response> login(@Valid @RequestBody AuthLoginRequest body) {
                ResponseCookie jwtCookie = authService.authenticate(body);
                return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                                .body(Response.builder().message("Login successful.").build());
        }

        /**
         * Registers a new user.
         *
         * @param body the registration request containing user details.
         * @return a success response with HTTP 201 status if registration is
         *         successful.
         * @throws Exception if registration fails.
         */
        @Operation(summary = "User Registration", description = "Register a new user and return a JWT token.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "User register successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class))),
                        @ApiResponse(responseCode = "409", description = "Conflict : User already exist with the same email in the system.", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorResponse.class))),
        })
        @PostMapping("register")
        public ResponseEntity<Response> register(@Valid @RequestBody UserRequest body) throws Exception {
                authService.register(body);
                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(Response.builder().message("Register successful.").build());
        }

        /**
         * Logs out the authenticated user by invalidating their JWT cookie.
         *
         * @return a response confirming the user has been logged out.
         */
        @Operation(summary = "User Logout", description = "Logout the current user.")
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "User Logout successfully", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Response.class))),
        })
        @GetMapping("logout")
        public ResponseEntity<Response> logout() {
                ResponseCookie logoutCookie = authService.logout();
                return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, logoutCookie.toString())
                                .body(Response.builder().message("Logout successful.").build());
        }

}
