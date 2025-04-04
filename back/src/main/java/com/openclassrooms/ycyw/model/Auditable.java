package com.openclassrooms.ycyw.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Abstract superclass for entities that require audit fields.
 * <p>
 * This class automatically manages creation and update timestamps
 * using Hibernate annotations. Entities extending this class will
 * inherit these fields.
 * </p>
 */
@Getter
@Setter
@MappedSuperclass
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
public abstract class Auditable {

    /**
     * Timestamp indicating when the entity was created.
     * <p>
     * This field is automatically set when the entity is first persisted
     * and cannot be updated.
     * </p>
     */
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * Timestamp indicating when the entity was last updated.
     * <p>
     * This field is automatically updated whenever the entity is modified.
     * </p>
     */
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;
}
