package com.travel.diary_service.mappers;

import com.travel.diary_service.dto.request.CommentRequest;
import com.travel.diary_service.dto.response.CommentResponse;
import com.travel.diary_service.entity.CommentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    CommentEntity toEntity(CommentRequest request);
    CommentResponse toResponse(CommentEntity comment);
}