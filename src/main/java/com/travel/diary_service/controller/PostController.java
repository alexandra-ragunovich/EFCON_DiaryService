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
    public DiaryPostResponse createPost(
            @RequestHeader("X-User-Id") Long userId,
            @Valid @RequestBody DiaryPostRequest request) {

        System.out.println(">>> X-User-Id = " + userId);
        request.setUserId(userId);
        return postService.createPost(request);
    }
    @PatchMapping("/{postId}")
    public DiaryPostResponse updatePost(
            @PathVariable Long postId,
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody DiaryPostRequest request
    ) {
        return postService.updatePost(postId, userId, request);
    }
    @GetMapping("/my")
    public List<DiaryPostResponse> getUserPosts( @RequestHeader("X-User-Id") Long userId) {
        return postService.getUserPosts(userId);
    }
    @GetMapping("/{postId}")
    public DiaryPostResponse getPostById(
            @PathVariable Long postId,
            @RequestHeader(value = "X-User-Id",required = false) Long userId
    ) {
        return postService.getPostById(postId, userId);
    }
    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id, @RequestHeader("X-User-Id") Long userId) {
        postService.deletePost(id,userId);
    }

    @DeleteMapping("/{postId}/photos/{photoId}")
    public void deletePhoto(
            @PathVariable Long postId,
            @PathVariable Long photoId,
            @RequestHeader("X-User-Id") Long userId) {
         postService.deletePhoto(postId, photoId, userId);
    }
    @GetMapping("/search")
    public List<DiaryPostResponse> searchPosts(@RequestParam("q") String keyword) {
        return postService.searchPosts(keyword);
    }

    @PostMapping("/{postId}/bookmark")
    public void savePostToBookmarks(
            @PathVariable Long postId,
            @RequestHeader("X-User-Id") Long userId) {
        postService.savePostToBookmarks(postId, userId);
    }

    @DeleteMapping("/{postId}/bookmark")
    public void removePostFromBookmarks(
            @PathVariable Long postId,
            @RequestHeader("X-User-Id") Long userId) {
        postService.removePostFromBookmarks(postId, userId);
    }

    @GetMapping("/bookmarks")
    public List<DiaryPostResponse> getBookmarkedPosts(
            @RequestHeader("X-User-Id") Long userId) {
        return postService.getBookmarkedPosts(userId);
    }

}
