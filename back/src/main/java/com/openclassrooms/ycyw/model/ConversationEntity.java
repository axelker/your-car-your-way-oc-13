package com.openclassrooms.ycyw.model;


import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


/**
 * Entity representing a scovnersation exchanged between users.
 */
@Entity
@Getter
@SuperBuilder(toBuilder = true)
@Table(name = "conversations")
@NoArgsConstructor
public class ConversationEntity extends Auditable {

    /**
     * Unique identifier for the Conversation.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    /**
     * Name of the conversation.
     */
    @Column(nullable = false, length = 50)
    private String name;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ConversationParticipantEntity> participants;


    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @OrderBy("sentAt ASC")
    private List<MessageEntity> messages;
    
}
