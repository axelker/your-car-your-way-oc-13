package com.openclassrooms.ycyw.service.query;

import java.util.List;

import org.springframework.stereotype.Service;

import com.openclassrooms.ycyw.dto.response.UserResponse;
import com.openclassrooms.ycyw.mapper.UserMapper;
import com.openclassrooms.ycyw.model.Role;
import com.openclassrooms.ycyw.repository.UserRepository;

@Service
public class SupportQueryService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public SupportQueryService(UserRepository userRepository,UserMapper userMapper){
        this.userRepository = userRepository;
        this.userMapper= userMapper;
    }

    public List<UserResponse> findAvailableUsersByRole(Role currentRole) {
        Role targetRole = switch (currentRole) {
            case CLIENT -> Role.SUPPORT;
            case SUPPORT -> Role.CLIENT;
            default -> throw new IllegalStateException("Unsupported role for support exchange.");
        };

        return userRepository.findByRole(targetRole).stream()
                .map(userMapper::toDto)
                .toList();
    }
    
}
