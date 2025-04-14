package com.openclassrooms.ycyw.service.command;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.openclassrooms.ycyw.dto.response.ConversationResponse;
import com.openclassrooms.ycyw.mapper.ConversationMapper;
import com.openclassrooms.ycyw.model.*;
import com.openclassrooms.ycyw.repository.*;

@Service
public class ConversationCommandService {

    private final ConversationRepository conversationRepository;
    private final ConversationParticipantRepository participantRepository;
    private final UserRepository userRepository;
    private final ConversationMapper conversationMapper;

    public ConversationCommandService(ConversationRepository conversationRepository,
                               ConversationParticipantRepository participantRepository,
                               UserRepository userRepository,
                               ConversationMapper conversationMapper) {
        this.conversationRepository = conversationRepository;
        this.participantRepository = participantRepository;
        this.userRepository = userRepository;
        this.conversationMapper = conversationMapper;
    }

    @Transactional
    public ConversationResponse startConversationBetweenTwoUser(Long userId1, Long userId2) {
        // Check if conversation already excist
        Optional<ConversationEntity> existingConversation = conversationRepository
                .findConversationWithExactParticipants(List.of(userId1,userId2),2);

        if (existingConversation.isPresent()) {
            return conversationMapper.toDto(existingConversation.get());
        }

        // Create conversation
        ConversationEntity conversation = ConversationEntity.builder().name("Conversation").messages(List.of()).build();
        conversation = conversationRepository.save(conversation);

        // Add participants
        UserEntity user1 = userRepository.findById(userId1).orElseThrow();
        UserEntity user2 = userRepository.findById(userId2).orElseThrow();

        ConversationParticipantId id1 = new ConversationParticipantId(userId1, conversation.getId());
        ConversationParticipantId id2 = new ConversationParticipantId(userId2, conversation.getId());

        ConversationParticipantEntity participant1 = ConversationParticipantEntity.builder()
                .id(id1)
                .user(user1)
                .conversation(conversation)
                .joinedAt(Instant.now())
                .build();

        ConversationParticipantEntity participant2 = ConversationParticipantEntity.builder()
                .id(id2)
                .user(user2)
                .conversation(conversation)
                .joinedAt(Instant.now())
                .build();

        participantRepository.saveAll(List.of(participant1, participant2));

        return conversationMapper.toDto(conversation);
    }

}