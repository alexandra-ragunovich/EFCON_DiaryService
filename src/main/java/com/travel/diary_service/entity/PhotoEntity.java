package com.travel.diary_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = PhotoEntity.TABLE_NAME)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PhotoEntity {
    public static final String TABLE_NAME = "photo";
    public static final String ID = "id";
    public static final String URL = "url";
    public static final String POST_ID = "post_id";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ID)
    private Long id;

    @Column(name = URL, nullable = false)
    private String url;

    @ManyToOne
    @JoinColumn(name = POST_ID)
    @JsonIgnore
    private DiaryPostEntity diaryPost;
}