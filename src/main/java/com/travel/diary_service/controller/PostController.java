package com.travel.diary_service.controller;

import com.travel.diary_service.dto.request.DiaryPostRequest;
import com.travel.diary_service.dto.response.DiaryPostResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import com.travel.diary_service.service.PostService;
import  java.util.List;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/feeds")
    public List<DiaryPostResponse> getPublicFeed(){
        return postService.getPublicFeed();
    }
    @PostMapping
    public DiaryPostResponse createPost( @Valid @RequestBody DiaryPostRequest request) {
        return postService.createPost(request);
    }
    @PatchMapping("/{postId}")
    public DiaryPostResponse updatePost(
            @PathVariable Long postId,
            @RequestParam Long userId,
            @RequestBody DiaryPostRequest request
    ) {
        return postService.updatePost(postId, userId, request);
    }
    @GetMapping("/users/{userId}")
    public List<DiaryPostResponse> getUserPosts(@PathVariable Long userId) {
        return postService.getUserPosts(userId);
    }
    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id,@RequestParam Long userId) {
        postService.deletePost(id,userId);
    }

    @DeleteMapping("/{postId}/photos/{photoId}")
    public void deletePhoto(
            @PathVariable Long postId,
            @PathVariable Long photoId,
            @RequestParam Long userId) {
         postService.deletePhoto(postId, photoId, userId);
    }

}
