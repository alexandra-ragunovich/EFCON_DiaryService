package com.travel.diary_service.repository;

import com.travel.diary_service.entity.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<TagEntity,Long> {
    Optional<TagEntity> findByName(String name);
}
