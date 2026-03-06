package com.travel.diary_service.controller;

import com.travel.diary_service.entity.Comment;
import com.travel.diary_service.service.CommentService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/posts/{postId}/comments")
    public Comment addComment(@PathVariable Long postId,
                              @RequestBody Comment comment) {
        return commentService.addComment(postId, comment);
    }
    @GetMapping("/posts/{postId}/comments")
    public List<Comment> getCommentsByPostId(@PathVariable Long postId){
        return commentService.getCommentsByPostId(postId);
    }
    @DeleteMapping("/comments/{commentId}")
    public void deleteComment( @PathVariable Long commentId, @RequestParam Long userId){
         commentService.deleteComment(commentId,userId);
    }
}
