package com.openclassrooms.ycyw.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

/**
 * Entity representing a log entry for a video support session between a client and a support agent.
 * <p>
 * This entity captures who participated, and when the call started and ended.
 * </p>
 */
@Entity
@Getter
@SuperBuilder(toBuilder = true)
@Table(name = "support_visio_logs")
@NoArgsConstructor
public class SupportVisioLogEntity extends Auditable {

    /**
     * Unique identifier for the visio session log.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The client who participated in the session.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    /**
     * The support agent who handled the session.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_support_id", nullable = false)
    private UserEntity userSupport;

    /**
     * Timestamp indicating when the session started.
     */
    @Column(name = "start_at", nullable = false)
    private Instant startAt;

    /**
     * Timestamp indicating when the session ended.
     */
    @Column(name = "end_at", nullable = false)
    private Instant endAt;
}
