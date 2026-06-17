package com.travel.diary_service.mappers;

import com.travel.diary_service.dto.request.CommentRequest;
import com.travel.diary_service.dto.response.CommentResponse;
import com.travel.diary_service.entity.CommentEntity;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

   public  CommentEntity toEntity(CommentRequest request){
       CommentEntity comment=new CommentEntity();
       comment.setUserId(request.getUserId());
       comment.setText(request.getText());
       return comment;
   };
   public  CommentResponse toResponse(CommentEntity comment) {
       CommentResponse response = new CommentResponse();
       response.setId(comment.getId());
       response.setUserId(comment.getUserId());
       response.setText(comment.getText());
       response.setCreatedAt(comment.getCreatedAt());
       return response;
   }
}