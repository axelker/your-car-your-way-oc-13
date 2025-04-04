package com.openclassrooms.ycyw.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

/**
 * Entity representing a support message exchanged between a user and the support team.
 * <p>
 * Each message contains a sender, a receiver, content, and a timestamp.
 * </p>
 */
@Entity
@Getter
@SuperBuilder(toBuilder = true)
@Table(name = "support_messages")
@NoArgsConstructor
public class SupportMessageEntity extends Auditable {

    /**
     * Unique identifier for the message.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The user who sent the message.
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private UserEntity sender;

    /**
     * The user who received the message (can be a support agent or a client).
     */
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", nullable = false)
    private UserEntity receiver;

    /**
     * Content of the message.
     */
    @Column(nullable = false, length = 2500)
    private String content;

    /**
     * Timestamp indicating when the message was sent.
     */
    @Column(name = "sent_at", nullable = false)
    private Instant sentAt;
}
