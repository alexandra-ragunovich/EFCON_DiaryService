package com.travel.diary_service.dto.request;
import lombok.Data;
import jakarta.validation.constraints.*;

import java.util.List;

@Data
public class DiaryPostRequest {
    @NotNull
    private Long userId;
    @NotBlank
    @Size(max = 200)
    private String title;
    @NotBlank
    private String content;
    @NotBlank
    private String location;
    private String date;
    private Integer rating;
    @NotNull
    private Boolean publicPost;
    private String duration;
    private String transportInfo;
    private Double expenses;
    private String tips;
    private List<String> photos;
    private List<String> tags;
}
