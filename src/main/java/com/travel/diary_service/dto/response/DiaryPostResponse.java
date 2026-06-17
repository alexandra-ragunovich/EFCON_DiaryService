package com.travel.diary_service.dto.response;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class DiaryPostResponse {
    private Long id;
    private String title;
    private String content;
    private String location;
    private String date;
    private Integer rating;
    private Boolean publicPost;
    private String duration;
    private String transportInfo;
    private Double expenses;
    private String tips;
    private LocalDateTime createdAt;
    private List<PhotoResponse> photos;
    private List<String> tags;

}
