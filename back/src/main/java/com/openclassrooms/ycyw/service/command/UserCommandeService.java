package com.openclassrooms.ycyw.service.command;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import com.openclassrooms.ycyw.dto.request.UserRequest;
import com.openclassrooms.ycyw.dto.response.UserResponse;
import com.openclassrooms.ycyw.mapper.UserMapper;
import com.openclassrooms.ycyw.model.UserEntity;
import com.openclassrooms.ycyw.repository.UserRepository;
import com.openclassrooms.ycyw.service.auth.JWTService;
import com.openclassrooms.ycyw.service.validation.UserValidationService;

/**
 * Service for handling user-related writable operations.
 * <p>
 * This service provides functionality to create new users and update existing ones,
 * ensuring that the necessary validation is done and JWT tokens are generated.
 * </p>
 */
@Service
public class UserCommandeService {

    private final UserValidationService userValidationService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JWTService jwtService;

    /**
     * Constructs an instance of {@code UserCommandeService} with required dependencies.
     *
     * @param userRepository         the repository for managing users.
     * @param userMapper             the mapper for converting between DTOs and entities.
     * @param userValidationService  the service for validating user data.
     * @param jwtService             the service for generating JWT tokens.
     */
    public UserCommandeService(UserRepository userRepository,
                               UserMapper userMapper,
                               UserValidationService userValidationService,
                               JWTService jwtService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userValidationService = userValidationService;
        this.jwtService = jwtService;
    }

    /**
     * Creates a new user.
     * <p>
     * This method validates the user data, then creates and saves the user in the database.
     * </p>
     *
     * @param request the user registration request.
     * @return a {@link UserResponse} representing the newly created user.
     */
    public UserResponse create(UserRequest request) {
        this.userValidationService.validateUser(request, null);

        UserEntity userToSave = userMapper.toEntity(request).toBuilder().build();
        return userMapper.toDto(userRepository.save(userToSave));
    }

    /**
     * Updates an existing user.
     * <p>
     * This method validates the user data, updates the user's information in the database,
     * and generates a new JWT cookie for the updated user.
     * </p>
     *
     * @param request the updated user data.
     * @param userId  the ID of the user to update.
     * @return a {@link ResponseCookie} containing the updated user's JWT token.
     */
    public ResponseCookie update(UserRequest request, Long userId) {
        this.userValidationService.validateUser(request, userId);

        UserEntity userToSave = userMapper.toEntity(request).toBuilder().id(userId).build();
        UserEntity savedUser = userRepository.save(userToSave);
        return jwtService.generateJwtCookie(savedUser);
    }
}
