package com.openclassrooms.ycyw.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.openclassrooms.ycyw.dto.request.MessageRequest;
import com.openclassrooms.ycyw.dto.response.SupportMessageResponse;
import com.openclassrooms.ycyw.model.MessageEntity;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    
    @Mapping(target = "sender.id", source = "senderId")
    @Mapping(target = "conversation.id", source = "conversationId")
    @Mapping(target = "sentAt", expression = "java(java.time.Instant.now())")
    MessageEntity toEntity(MessageRequest dto);

    @Mapping(target = "username", source = "sender.email")
    SupportMessageResponse toDto(MessageEntity entity);

    List<SupportMessageResponse> toDtoList(List<MessageEntity> entities);
}
