package com.travel.diary_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "saved_posts", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "post_id"}) // Один пользователь может сохранить пост только 1 раз
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SavedPostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private DiaryPostEntity post;

    @CreationTimestamp
    @Column(name = "saved_at")
    private LocalDateTime savedAt;
}