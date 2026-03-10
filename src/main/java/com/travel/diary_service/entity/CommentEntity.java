package com.travel.diary_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = CommentEntity.TABLE_NAME)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentEntity {

    public static final String TABLE_NAME = "comment";
    public static final String ID = "id";
    public static final String USER_ID = "user_id";
    public static final String TEXT = "text";
    public static final String CREATED_AT = "created_at";
    public static final String POST_ID = "post_id";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID)
    private Long id;

    @Column(name = USER_ID, nullable = false)
    private Long userId;

    @Column(name = TEXT, columnDefinition = "TEXT", nullable = false)
    private String text;

    @CreationTimestamp
    @Column(name = CREATED_AT)
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = POST_ID, nullable = false)
    @JsonIgnore
    private DiaryPostEntity post;
}