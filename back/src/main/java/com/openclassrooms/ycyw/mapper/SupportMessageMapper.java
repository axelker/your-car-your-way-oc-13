package com.openclassrooms.ycyw.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.openclassrooms.ycyw.dto.request.SupportMessageRequest;
import com.openclassrooms.ycyw.dto.response.SupportMessageResponse;
import com.openclassrooms.ycyw.model.SupportMessageEntity;

@Mapper(componentModel = "spring")
public interface SupportMessageMapper {
    
    @Mapping(target = "sender.id", source = "senderId")
    @Mapping(target = "receiver.id", source = "receiverId")
    @Mapping(target = "sentAt", expression = "java(java.time.Instant.now())")
    SupportMessageEntity toEntity(SupportMessageRequest dto);

    @Mapping(target = "username", source = "sender.email")
    SupportMessageResponse toDto(SupportMessageEntity entity);
}
