package com.travel.diary_service.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import  java.util.List;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiaryPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private String title;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String location;
    @Column
    private String date;
    @Column
    private Integer rating;
    @Column(nullable = false)
    private Boolean publicPost = true;
    @Column
    private String duration;
    @Column
    private String transportInfo;
    @Column
    private Double expenses;
    @Column(columnDefinition = "TEXT")
    private String tips;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "diaryPost", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Photo> photos;
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )

    private List<Tag> tags;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Comment> comments;

}
