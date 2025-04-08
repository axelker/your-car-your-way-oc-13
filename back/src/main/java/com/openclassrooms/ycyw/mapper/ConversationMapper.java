package com.openclassrooms.ycyw.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.openclassrooms.ycyw.dto.response.ConversationResponse;
import com.openclassrooms.ycyw.model.ConversationEntity;

@Mapper(componentModel = "spring", uses = MessageMapper.class)
public interface ConversationMapper {

    @Mapping(target = "messages", source = "messages")
    ConversationResponse toDto(ConversationEntity entity);
}
