package com.openclassrooms.ycyw.service.query;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.openclassrooms.ycyw.dto.response.ConversationResponse;
import com.openclassrooms.ycyw.mapper.ConversationMapper;
import com.openclassrooms.ycyw.model.ConversationEntity;
import com.openclassrooms.ycyw.repository.ConversationRepository;


@Service
public class ConversationQueryService {

    private final ConversationRepository conversationRepository;
    private final ConversationMapper conversationMapper;

    ConversationQueryService(ConversationRepository conversationRepository,
            ConversationMapper conversationMapper) {
        this.conversationRepository = conversationRepository;
        this.conversationMapper = conversationMapper;
    }


    public List<ConversationResponse> findAllByUser(Long userId) {
        return conversationRepository
                .findAllByParticipantsUserId(userId)
                .stream()
                .map(conversationMapper::toDto)
                .toList();
    }


    public ConversationResponse findById(Long conversationId) {
        ConversationEntity entity = conversationRepository.findById(conversationId)
                .orElseThrow(() -> new NoSuchElementException("Conversation not found"));
        return conversationMapper.toDto(entity);
    }

}
