package com.travel.diary_service.controller;

import com.travel.diary_service.entity.DiaryPost;
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

    @GetMapping("/feed")
    public List<DiaryPost> getPublicFeed(){
        return postService.getPublicFeed();
    }
    @PostMapping
    public DiaryPost createPost(@RequestBody DiaryPost post) {
        return postService.createPost(post);
    }
    @PatchMapping("/{postId}")
    public DiaryPost updatePost(
            @PathVariable Long postId,
            @RequestParam Long userId,
            @RequestBody DiaryPost post
    ) {
        return postService.updatePost(postId, userId, post);
    }
    @GetMapping("/users/{userId}")
    public List<DiaryPost> getUserPosts(@PathVariable Long userId) {
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
