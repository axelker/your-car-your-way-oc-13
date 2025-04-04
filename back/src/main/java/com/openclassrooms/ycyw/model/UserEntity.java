package com.openclassrooms.ycyw.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Entity representing a user in the system.
 * <p>
 * This entity extends {@link Auditable}, which manages the creation and update timestamps.
 * It also implements {@link UserDetails} from Spring Security to handle authentication.
 * </p>
 */
@Entity
@Getter
@SuperBuilder(toBuilder = true)
@Table(name = "users")
@NoArgsConstructor
public class UserEntity extends Auditable implements UserDetails {

    /**
     * The unique identifier of the user.
     * <p>
     * Generated automatically by the database using an identity strategy.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The email address of the user.
     * <p>
     * Must be unique and cannot be null.
     * </p>
     */
    @Column(nullable = false, unique = true)
    private String email;


    /**
     * The hashed password of the user.
     * <p>
     * Cannot be null and should be securely stored.
     * </p>
     */
    @Column(nullable = false)
    private String password;

    /**
     * Returns the authorities granted to the user.
     * <p>
     * Currently, this method returns an empty list as roles/authorities are not managed.
     * </p>
     *
     * @return an empty list of {@link GrantedAuthority}.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    /**
     * Returns the username used to authenticate the user.
     *
     * @return the username of the user.
     */
    @Override
    public String getUsername() {
        return this.email;
    }
}
