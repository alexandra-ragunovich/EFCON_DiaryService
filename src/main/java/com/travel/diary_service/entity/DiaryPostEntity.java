package com.travel.diary_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "diary_post")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiaryPostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "location", columnDefinition = "TEXT", nullable = false)
    private String location;

    @Column(name = "travel_date")
    private String date;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "is_public", nullable = false)
    private Boolean publicPost = true;

    @Column(name = "duration")
    private String duration;

    @Column(name = "transport_info")
    private String transportInfo;

    @Column(name = "expenses")
    private Double expenses;

    @Column(name = "tips", columnDefinition = "TEXT")
    private String tips;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "diaryPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhotoEntity> photos;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<TagEntity> tags;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentEntity> comment;
}