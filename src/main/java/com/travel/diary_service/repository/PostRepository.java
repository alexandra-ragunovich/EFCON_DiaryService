package com.travel.diary_service.repository;
import java.util.List;
import com.travel.diary_service.entity.DiaryPost;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PostRepository extends JpaRepository<DiaryPost,Long>{
    List<DiaryPost> findByPublicPostTrueOrderByCreatedAtDesc();
    List<DiaryPost> findByUserIdOrderByCreatedAtDesc(Long userId);
}
