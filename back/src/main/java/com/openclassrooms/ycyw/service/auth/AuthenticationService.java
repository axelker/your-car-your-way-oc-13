package com.openclassrooms.ycyw.service.auth;

import java.util.NoSuchElementException;

import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.openclassrooms.ycyw.dto.request.AuthLoginRequest;
import com.openclassrooms.ycyw.dto.request.UserRequest;
import com.openclassrooms.ycyw.dto.response.UserResponse;
import com.openclassrooms.ycyw.exception.UnauthorizedActionException;
import com.openclassrooms.ycyw.model.UserEntity;
import com.openclassrooms.ycyw.repository.UserRepository;
import com.openclassrooms.ycyw.service.command.UserCommandeService;

/**
 * Service handling user authentication and registration.
 * <p>
 * This service provides methods to register new users, authenticate existing users, 
 * and handle user logout using JWT-based authentication.
 * </p>
 */
@Service
public class AuthenticationService {

    private final UserCommandeService userCommandeService;
    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    /**
     * Constructs an instance of {@code AuthenticationService} with required dependencies.
     *
     * @param userCommandeService     the service responsible for user creation.
     * @param userRepository          the repository for retrieving user data.
     * @param jwtService              the service responsible for JWT operations.
     * @param authenticationManager   the authentication manager for verifying credentials.
     */
    public AuthenticationService(
            UserCommandeService userCommandeService,
            UserRepository userRepository,
            JWTService jwtService,
            AuthenticationManager authenticationManager) {
        this.userCommandeService = userCommandeService;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * Registers a new user and generates a JWT cookie for authentication.
     * <p>
     * This method first creates the user, then retrieves it from the database
     * to ensure consistency, and finally generates a JWT authentication cookie.
     * </p>
     *
     * @param request the user registration request containing email, username, and password.
     * @return a {@link ResponseCookie} containing the JWT for authentication.
     * @throws Exception if the user cannot be created.
     */
    public ResponseCookie register(UserRequest request) throws Exception {
        UserResponse userResponse = this.userCommandeService.create(request);
        UserEntity user = userRepository.findById(userResponse.getId())
                .orElseThrow(() -> new NoSuchElementException("User not found after creation."));
        return jwtService.generateJwtCookie(user);
    }

    /**
     * Authenticates a user based on their credentials and generates a JWT cookie.
     * <p>
     * This method validates the user's email/username and password using Spring Security.
     * If the credentials are correct, a JWT authentication cookie is returned.
     * </p>
     *
     * @param request the login request containing the user's identifier (email/username) and password.
     * @return a {@link ResponseCookie} containing the JWT for authentication.
     * @throws BadCredentialsException if the provided credentials are invalid.
     */
    public ResponseCookie authenticate(AuthLoginRequest request) {
        try {
            var authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getIdentifier(),
                            request.getPassword()));

            UserEntity user = (UserEntity) authentication.getPrincipal();
            return jwtService.generateJwtCookie(user);

        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid email or password.");
        }
    }

    /**
     * Logs out the user by invalidating their authentication cookie.
     *
     * @return a {@link ResponseCookie} configured to remove the JWT from the client.
     */
    public ResponseCookie logout() {
        return jwtService.generateJwtLogoutCookie();
    }

    /**
     * Return the id of Authenticated user.
     * @return
     */
    public Long getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof UserEntity)) {
            throw new UnauthorizedActionException("User is not authenticated.");
        }

        Long userId = ((UserEntity) authentication.getPrincipal()).getId();
        return userId;
    }
}
