package com.travel.diary_service.service;

import com.travel.diary_service.entity.Comment;
import com.travel.diary_service.entity.DiaryPost;
import com.travel.diary_service.repository.CommentRepository;
import com.travel.diary_service.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public Comment addComment(Long postId, Comment comment) {
        DiaryPost post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        comment.setPost(post);
        return commentRepository.save(comment);
    }

    public List<Comment> getCommentsByPostId(Long postId) {

        return commentRepository.findByPostIdOrderByCreatedAtAsc(postId);
    }
    public void deleteComment(Long commentId, Long userId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!comment.getUserId().equals(userId)) {
            throw new RuntimeException("You can not delete this comment");
        }

        commentRepository.delete(comment);
    }
}