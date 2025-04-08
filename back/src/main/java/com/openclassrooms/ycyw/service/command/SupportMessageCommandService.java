package com.openclassrooms.ycyw.service.command;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.openclassrooms.ycyw.dto.request.MessageRequest;
import com.openclassrooms.ycyw.dto.response.SupportMessageResponse;
import com.openclassrooms.ycyw.mapper.MessageMapper;
import com.openclassrooms.ycyw.model.MessageEntity;
import com.openclassrooms.ycyw.model.UserEntity;
import com.openclassrooms.ycyw.repository.MessageRepository;
import com.openclassrooms.ycyw.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class SupportMessageCommandService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final SimpMessagingTemplate messagingTemplate;
    private final UserRepository userRepository;

    SupportMessageCommandService(MessageRepository messageRepository,MessageMapper messageMapper,SimpMessagingTemplate messagingTemplate,UserRepository userRepository){
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
        this.messagingTemplate = messagingTemplate;
        this.userRepository = userRepository;
    }

    @Transactional
    public void handleAndBroadcastSupportMessage(MessageRequest dto) {
        UserEntity sender = userRepository.findById(dto.getSenderId())
            .orElseThrow(() -> new RuntimeException("User sender not found."));        
        
        MessageEntity entity = messageMapper.toEntity(dto);
        MessageEntity savedEntity = messageRepository.save(entity);

        SupportMessageResponse res = SupportMessageResponse.builder().username(sender.getUsername()).content(savedEntity.getContent()).build();

        messagingTemplate.convertAndSend("/topic/support/" + dto.getConversationId(), res);
    }
}
