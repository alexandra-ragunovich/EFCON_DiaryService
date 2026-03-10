package com.travel.diary_service.dto.response;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CommentResponse {
    private Long id;
    private Long userId;
    private String text;
    private LocalDateTime createdAt;
}
