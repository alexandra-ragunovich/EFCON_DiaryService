package com.travel.diary_service.repository;
import java.util.List;
import com.travel.diary_service.entity.DiaryPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PostRepository extends JpaRepository<DiaryPostEntity,Long>{
    List<DiaryPostEntity> findByPublicPostTrueOrderByCreatedAtDesc();
    List<DiaryPostEntity> findByUserIdOrderByCreatedAtDesc(Long userId);
}
