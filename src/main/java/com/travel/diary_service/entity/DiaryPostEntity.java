package com.travel.diary_service.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = DiaryPostEntity.TABLE_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiaryPostEntity {
    public static final String TABLE_NAME = "diary_post";
    public static final String TABLE_POST_TAGS = "post_tags";
    public static final String ID = "id";
    public static final String USER_ID = "user_id";
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String LOCATION = "location";
    public static final String TRAVEL_DATE = "travel_date";
    public static final String RATING = "rating";
    public static final String IS_PUBLIC = "is_public";
    public static final String DURATION = "duration";
    public static final String TRANSPORT_INFO = "transport_info";
    public static final String EXPENSES = "expenses";
    public static final String TIPS = "tips";
    public static final String CREATED_AT = "created_at";
    public static final String JOIN_POST_ID = "post_id";
    public static final String JOIN_TAG_ID = "tag_id";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID)
    private Long id;

    @Column(name = USER_ID, nullable = false)
    private Long userId;

    @Column(name = TITLE, nullable = false)
    private String title;

    @Column(name = CONTENT, columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = LOCATION, columnDefinition = "TEXT", nullable = false)
    private String location;

    @Column(name = TRAVEL_DATE)
    private String date;

    @Column(name = RATING)
    private Integer rating;

    @Column(name = IS_PUBLIC, nullable = false)
    private Boolean publicPost = true;

    @Column(name = DURATION)
    private String duration;

    @Column(name = TRANSPORT_INFO)
    private String transportInfo;

    @Column(name = EXPENSES)
    private Double expenses;

    @Column(name = TIPS, columnDefinition = "TEXT")
    private String tips;

    @CreationTimestamp
    @Column(name = CREATED_AT)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "diaryPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhotoEntity> photos;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = TABLE_POST_TAGS,
            joinColumns = @JoinColumn(name = JOIN_POST_ID),
            inverseJoinColumns = @JoinColumn(name = JOIN_TAG_ID)
    )
    private List<TagEntity> tags;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentEntity> comment;
}