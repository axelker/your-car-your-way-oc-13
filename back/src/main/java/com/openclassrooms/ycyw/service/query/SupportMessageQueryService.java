package com.openclassrooms.ycyw.service.query;

import java.util.List;

import org.springframework.stereotype.Service;

import com.openclassrooms.ycyw.dto.response.SupportMessageResponse;
import com.openclassrooms.ycyw.mapper.SupportMessageMapper;
import com.openclassrooms.ycyw.repository.SupportMessageRepository;

@Service
public class SupportMessageQueryService {
    private final SupportMessageRepository supportMessageRepository;
    private final SupportMessageMapper supportMessageMapper;
    SupportMessageQueryService(SupportMessageRepository supportMessageRepository,SupportMessageMapper supportMessageMapper){
        this.supportMessageRepository = supportMessageRepository;
        this.supportMessageMapper = supportMessageMapper;
    }

    public List<SupportMessageResponse> getChatBetweenUsers(Long user1, Long user2) {
        return supportMessageRepository
                .findChatBetweenUsers(user1, user2)
                .stream()
                .map(supportMessageMapper::toDto)
                .toList();
    }
}
