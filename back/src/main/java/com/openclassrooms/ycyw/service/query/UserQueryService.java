package com.openclassrooms.ycyw.service.query;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.openclassrooms.ycyw.dto.response.UserResponse;
import com.openclassrooms.ycyw.mapper.UserMapper;
import com.openclassrooms.ycyw.model.UserEntity;
import com.openclassrooms.ycyw.repository.UserRepository;

/**
 * Service for querying user information.
 * <p>
 * This service provides methods to fetch user details by ID or username,
 * and it implements the {@link UserDetailsService} interface to load user details
 * for authentication purposes.
 * </p>
 */
@Service
public class UserQueryService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Constructs an instance of {@code UserQueryService} with required dependencies.
     *
     * @param userRepository the repository for managing user data.
     * @param userMapper     the mapper for converting between entities and DTOs.
     */
    public UserQueryService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    /**
     * Finds a user by their ID.
     * <p>
     * This method fetches the user from the database using the provided ID. If the user
     * is not found, a {@link NoSuchElementException} is thrown.
     * </p>
     *
     * @param id the ID of the user.
     * @return a {@link UserResponse} representing the user.
     * @throws NoSuchElementException if the user with the provided ID is not found.
     */
    public UserResponse findById(Long id) throws NoSuchElementException {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));
        return userMapper.toDto(user);
    }

    /**
     * Loads user details by their username.
     * <p>
     * This method checks if the username is an email or username. If it's an email, it searches for the user
     * by email. Otherwise, it searches for the user by username. If no user is found, a {@link UsernameNotFoundException}
     * is thrown.
     * </p>
     *
     * @param username the email of the user.
     * @return the user details for authentication purposes.
     * @throws UsernameNotFoundException if no user is found with the provided username or email.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = userRepository.findByEmail(username);
        return user.orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }
}
