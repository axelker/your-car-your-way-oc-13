package com.openclassrooms.ycyw.service.command;

import org.springframework.stereotype.Service;

import com.openclassrooms.ycyw.dto.request.SupportRequest;
import com.openclassrooms.ycyw.mapper.SupportRequestMapper;
import com.openclassrooms.ycyw.model.SupportRequestEntity;
import com.openclassrooms.ycyw.model.SupportRequestStatus;
import com.openclassrooms.ycyw.repository.SupportRequestRepository;

@Service
public class SupportRequestCommandService {
    private final SupportRequestRepository supportRequestRepository;
    private final SupportRequestMapper supportRequestMapper;

    SupportRequestCommandService(SupportRequestRepository supportRequestRepository,SupportRequestMapper supportRequestMapper) {
        this.supportRequestRepository = supportRequestRepository;
        this.supportRequestMapper = supportRequestMapper;
    }

    public void createRequest(Long userId,SupportRequest request){
        SupportRequestEntity entity = this.supportRequestMapper.toEntity(request, userId).toBuilder().status(SupportRequestStatus.OPEN).build();
        supportRequestRepository.save(entity);
    }
}
