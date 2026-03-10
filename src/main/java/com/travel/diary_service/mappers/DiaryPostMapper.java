package com.travel.diary_service.mappers;

import com.travel.diary_service.dto.request.DiaryPostRequest;
import com.travel.diary_service.dto.response.DiaryPostResponse;
import com.travel.diary_service.dto.response.PhotoResponse;
import com.travel.diary_service.entity.DiaryPostEntity;
import com.travel.diary_service.entity.PhotoEntity;
import com.travel.diary_service.entity.TagEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class DiaryPostMapper {
    public DiaryPostEntity toEntity(DiaryPostRequest request) {
        DiaryPostEntity diaryPostEntity = new DiaryPostEntity();
        diaryPostEntity.setUserId(request.getUserId());
        diaryPostEntity.setTitle(request.getTitle());
        diaryPostEntity.setContent(request.getContent());
        diaryPostEntity.setLocation(request.getLocation());
        diaryPostEntity.setDate(request.getDate());
        diaryPostEntity.setRating(request.getRating());
        diaryPostEntity.setPublicPost(request.getPublicPost());
        diaryPostEntity.setDuration(request.getDuration());
        diaryPostEntity.setTransportInfo(request.getTransportInfo());
        diaryPostEntity.setExpenses(request.getExpenses());
        diaryPostEntity.setTips(request.getTips());
        return diaryPostEntity;
    }

    public DiaryPostResponse toResponse(DiaryPostEntity post) {

        DiaryPostResponse response = new DiaryPostResponse();
        response.setId(post.getId());
        response.setUserId(post.getUserId());
        response.setTitle(post.getTitle());
        response.setContent(post.getContent());
        response.setLocation(post.getLocation());
        response.setDate(post.getDate());
        response.setRating(post.getRating());
        response.setPublicPost(post.getPublicPost());
        response.setDuration(post.getDuration());
        response.setTransportInfo(post.getTransportInfo());
        response.setExpenses(post.getExpenses());
        response.setTips(post.getTips());
        response.setCreatedAt(post.getCreatedAt());

        if (post.getTags() != null) {
            response.setTags(
                    post.getTags()
                            .stream()
                            .map(TagEntity::getName)
                            .collect(Collectors.toList())
            );
        }

        if (post.getPhotos() != null) {
            response.setPhotos(
                    post.getPhotos()
                            .stream()
                            .map(this::mapPhotoToResponse)
                            .collect(Collectors.toList())
            );
        }

        return response;
    }
    public void updateEntityFromRequest(DiaryPostRequest request, DiaryPostEntity diaryPostEntity) {

        Optional.ofNullable(request.getTitle()).ifPresent(diaryPostEntity::setTitle);
        Optional.ofNullable(request.getContent()).ifPresent(diaryPostEntity::setContent);
        Optional.ofNullable(request.getLocation()).ifPresent(diaryPostEntity::setLocation);
        Optional.ofNullable(request.getDate()).ifPresent(diaryPostEntity::setDate);
        Optional.ofNullable(request.getRating()).ifPresent(diaryPostEntity::setRating);
        Optional.ofNullable(request.getPublicPost()).ifPresent(diaryPostEntity::setPublicPost);
        Optional.ofNullable(request.getDuration()).ifPresent(diaryPostEntity::setDuration);
        Optional.ofNullable(request.getTransportInfo()).ifPresent(diaryPostEntity::setTransportInfo);
        Optional.ofNullable(request.getExpenses()).ifPresent(diaryPostEntity::setExpenses);
        Optional.ofNullable(request.getTips()).ifPresent(diaryPostEntity::setTips);
    }
    public PhotoResponse mapPhotoToResponse(PhotoEntity photoEntity) {

        PhotoResponse response = new PhotoResponse();
        response.setId(photoEntity.getId());
        response.setUrl(photoEntity.getUrl());

        return response;
    }

}
