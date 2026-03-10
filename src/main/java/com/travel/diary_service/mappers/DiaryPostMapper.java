package com.travel.diary_service.mappers;

import com.travel.diary_service.dto.request.DiaryPostRequest;
import com.travel.diary_service.dto.response.DiaryPostResponse;
import com.travel.diary_service.dto.response.PhotoResponse;
import com.travel.diary_service.entity.DiaryPostEntity;
import com.travel.diary_service.entity.PhotoEntity;
import com.travel.diary_service.entity.TagEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DiaryPostMapper {
    @Mapping(target = "photos", ignore = true)
    @Mapping(target = "tags", ignore = true)
    DiaryPostEntity toEntity(DiaryPostRequest request);
    DiaryPostResponse toResponse(DiaryPostEntity post);
    @Mapping(target = "photos", ignore = true)
    @Mapping(target = "tags", ignore = true)
    void updateEntityFromRequest(DiaryPostRequest request, @MappingTarget DiaryPostEntity post);
    default String mapTagToString(TagEntity tagEntity) {
        return tagEntity != null ? tagEntity.getName() : null;
    }
    PhotoResponse mapPhotoToResponse(PhotoEntity photoEntity);
}
