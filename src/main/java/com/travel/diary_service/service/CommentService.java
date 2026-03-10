package com.travel.diary_service.service;

import com.travel.diary_service.dto.request.CommentRequest;
import com.travel.diary_service.dto.response.CommentResponse;
import com.travel.diary_service.entity.CommentEntity;
import com.travel.diary_service.entity.DiaryPostEntity;
import com.travel.diary_service.mappers.CommentMapper;
import com.travel.diary_service.repository.CommentRepository;
import com.travel.diary_service.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;


    @Transactional
    public CommentResponse addComment(Long postId, CommentRequest request) {
        DiaryPostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        CommentEntity commentEntity =commentMapper.toEntity(request);
        commentEntity.setPost(post);
        CommentEntity saved =commentRepository.save(commentEntity);
        return commentMapper.toResponse(saved);
    }

    public List<CommentResponse> getCommentsByPostId(Long postId) {

        return  commentRepository.findByPostIdOrderByCreatedAtAsc(postId).stream().map(commentMapper::toResponse).collect(Collectors.toList());
    }
    public void deleteComment(Long commentId, Long userId) {
        CommentEntity commentEntity = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!commentEntity.getUserId().equals(userId)) {
            throw new RuntimeException("You are not the owner of this comment");
        }

        commentRepository.delete(commentEntity);
    }
}