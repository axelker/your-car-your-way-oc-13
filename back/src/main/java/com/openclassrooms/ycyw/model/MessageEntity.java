package com.openclassrooms.ycyw.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

/**
 * Entity representing a message exchanged between user.
 */
@Entity
@Getter
@SuperBuilder(toBuilder = true)
@Table(name = "messages")
@NoArgsConstructor
public class MessageEntity extends Auditable {

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id")
    private ConversationEntity conversation;

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
