package com.openclassrooms.ycyw.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Mapper for encoding passwords using Spring Security's {@link PasswordEncoder}.
 * <p>
 * This abstract class provides a method to encode passwords and is meant to be used 
 * with MapStruct for automatic transformation of raw passwords into encoded values.
 * </p>
 */
@Mapper(componentModel = "spring")
public abstract class PasswordEncoderMapper {

    /**
     * The password encoder instance, injected lazily to avoid circular dependencies.
     */
    @Autowired
    @Lazy
    protected PasswordEncoder passwordEncoder;

    /**
     * Encodes a plain text password.
     * <p>
     * This method is used by MapStruct to automatically encode passwords when mapping 
     * user-related DTOs to entities.
     * </p>
     *
     * @param password the raw password to be encoded.
     * @return the encoded password.
     */
    @Named("encodePassword")
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
