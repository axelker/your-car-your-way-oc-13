package com.openclassrooms.ycyw.service.command;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.openclassrooms.ycyw.dto.request.SupportMessageRequest;
import com.openclassrooms.ycyw.dto.response.SupportMessageResponse;
import com.openclassrooms.ycyw.mapper.SupportMessageMapper;
import com.openclassrooms.ycyw.model.SupportMessageEntity;
import com.openclassrooms.ycyw.model.UserEntity;
import com.openclassrooms.ycyw.repository.SupportMessageRepository;
import com.openclassrooms.ycyw.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class SupportMessageCommandService {

    private final SupportMessageRepository supportMessageRepository;
    private final SupportMessageMapper supportMessageMapper;
    private final SimpMessagingTemplate messagingTemplate;
    private final UserRepository userRepository;

    SupportMessageCommandService(SupportMessageRepository supportMessageRepository,SupportMessageMapper supportMessageMapper,SimpMessagingTemplate messagingTemplate,UserRepository userRepository){
        this.supportMessageRepository = supportMessageRepository;
        this.supportMessageMapper = supportMessageMapper;
        this.messagingTemplate = messagingTemplate;
        this.userRepository = userRepository;
    }

    @Transactional
    public void handleAndBroadcastMessage(SupportMessageRequest dto) {
        UserEntity sender = userRepository.findById(dto.getSenderId())
            .orElseThrow(() -> new RuntimeException("User sender not found."));        
        
        SupportMessageEntity entity = supportMessageMapper.toEntity(dto);
        SupportMessageEntity savedEntity = supportMessageRepository.save(entity);

        SupportMessageResponse res = SupportMessageResponse.builder().username(sender.getUsername()).content(savedEntity.getContent()).build();

        //TODO: use conversation ID when available
        messagingTemplate.convertAndSend("/topic/support/" + dto.getReceiverId(), res);
    }
}
