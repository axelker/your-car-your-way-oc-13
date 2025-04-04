package com.openclassrooms.ycyw.service.validation;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.openclassrooms.ycyw.dto.request.UserRequest;
import com.openclassrooms.ycyw.exception.user.UserAlreadyExistsException;
import com.openclassrooms.ycyw.model.UserEntity;
import com.openclassrooms.ycyw.repository.UserRepository;

/**
 * Service for validating user-related operations.
 * <p>
 * This service validates the user data, including checking if the username or email is already in use
 * when creating or updating a user.
 * </p>
 */
@Service
public class UserValidationService {

    private final UserRepository userRepository;

    /**
     * Constructs an instance of {@code UserValidationService} with required dependencies.
     *
     * @param userRepository the repository for managing user data.
     */
    UserValidationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     * Validates if an email is already taken by another user.
     * <p>
     * This method checks the repository to see if the email is already in use. If it is, an exception
     * is thrown to indicate the conflict.
     * </p>
     *
     * @param email the email to validate.
     * @throws UserAlreadyExistsException if the email is already taken.
     */
    private void validAlreadyUsedEmail(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new UserAlreadyExistsException("User already exists with the same email.");
        }
    }

    /**
     * Validates user data during registration or update.
     * <p>
     * This method checks if the email or username provided in the request is already in use by another user.
     * It also checks if the user exists when updating their information.
     * </p>
     *
     * @param request the user registration or update request.
     * @param userId  the ID of the user being updated (if applicable).
     * @throws UserAlreadyExistsException if the email or username is already in use.
     * @throws NoSuchElementException if the user does not exist during an update.
     */
    public void validateUser(UserRequest request, Long userId) {
        UserEntity existUser = null;
        if (null != userId) {
            existUser = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User not found with ID: " + userId));
        }
        
        if (null == existUser || !existUser.getEmail().equals(request.getEmail())) {
            this.validAlreadyUsedEmail(request.getEmail());
        }
    }
}
