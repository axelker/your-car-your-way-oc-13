package com.openclassrooms.ycyw.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.openclassrooms.ycyw.dto.request.SupportRequest;
import com.openclassrooms.ycyw.model.SupportRequestEntity;


@Mapper(componentModel = "spring")
public interface SupportRequestMapper {
    SupportRequestMapper INSTANCE = Mappers.getMapper(SupportRequestMapper.class);


    @Mapping(target = "user.id", source = "userId")
    SupportRequestEntity toEntity(SupportRequest dto,Long userId);
}
