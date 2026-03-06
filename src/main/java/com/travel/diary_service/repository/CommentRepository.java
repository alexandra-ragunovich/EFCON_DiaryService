package com.travel.diary_service.repository;
import com.travel.diary_service.entity.Comment;
import com.travel.diary_service.entity.DiaryPost;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostIdOrderByCreatedAtAsc(Long postId);
}
