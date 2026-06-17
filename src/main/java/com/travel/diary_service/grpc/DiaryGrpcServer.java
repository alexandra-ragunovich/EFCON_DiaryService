package com.travel.diary_service.grpc;

import com.travel.diary_service.dto.request.DiaryPostRequest;
import com.travel.diary_service.dto.response.DiaryPostResponse;
import com.travel.diary_service.service.PostService;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import com.travel.grpc.*;
import java.util.ArrayList;

@GrpcService
@RequiredArgsConstructor
public class DiaryGrpcServer extends DiaryGrpcServiceGrpc.DiaryGrpcServiceImplBase {

    private final PostService postService;

    @Override
    public void createDiaryPost(GrpcDiaryPostRequest request, StreamObserver<GrpcDiaryPostResponse> responseObserver) {

        DiaryPostRequest postDto = new DiaryPostRequest();
        postDto.setUserId(request.getUserId());
        postDto.setTitle(request.getTitle());
        postDto.setContent(request.getContent());
        postDto.setLocation(request.getLocation());
        postDto.setPublicPost(request.getIsPublic());
        postDto.setTransportInfo(request.getTransportInfo());
        postDto.setDuration(request.getDuration());
        postDto.setPhotos(new ArrayList<>());
        postDto.setTags(new ArrayList<>());

        DiaryPostResponse createdPost = postService.createPost(postDto);

        GrpcDiaryPostResponse grpcResponse = GrpcDiaryPostResponse.newBuilder()
                .setPostId(createdPost.getId())
                .setStatus("SUCCESS")
                .build();

        responseObserver.onNext(grpcResponse);
        responseObserver.onCompleted();
    }
}