package com.travel.diary_service.repository;

import com.travel.diary_service.entity.SavedPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SavedPostRepository extends JpaRepository<SavedPostEntity, Long> {
    Optional<SavedPostEntity> findByUserIdAndPostId(Long userId, Long postId);
    List<SavedPostEntity> findByUserIdOrderBySavedAtDesc(Long userId);
    boolean existsByUserIdAndPostId(Long userId, Long postId);
}