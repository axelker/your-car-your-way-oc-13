package com.openclassrooms.ycyw.model;


import java.time.Instant;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * Entity representing a participant (user) inside a conversation.
 */
@Entity
@Getter
@SuperBuilder(toBuilder = true)
@Table(name = "conversation_participants")
@NoArgsConstructor
public class ConversationParticipantEntity {
   
    @EmbeddedId
    private ConversationParticipantId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("conversationId")
    @JoinColumn(name = "conversation_id", nullable = false)
    private ConversationEntity conversation;

    @Column(name = "joined_at", nullable = false)
    private Instant joinedAt;
}
