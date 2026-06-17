package com.travel.diary_service.controller;

import com.travel.diary_service.dto.request.CommentRequest;
import com.travel.diary_service.dto.response.CommentResponse;
import com.travel.diary_service.service.CommentService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/{postId}/comments")
    public CommentResponse addComment(@PathVariable Long postId,
                                      @RequestHeader("X-User-Id") Long userId,
                                      @Valid @RequestBody CommentRequest request) {
        request.setUserId(userId);
        return commentService.addComment(postId, request);
    }
    @GetMapping("/{postId}/comments")
    public List<CommentResponse> getCommentsByPostId(@PathVariable Long postId){
        return commentService.getCommentsByPostId(postId);
    }
    @DeleteMapping("/comments/{commentId}")
    public void deleteComment( @PathVariable Long commentId,
                               @RequestHeader("X-User-Id") Long userId){
         commentService.deleteComment(commentId,userId);
    }
}
