package com.travel.diary_service.service;

import com.travel.diary_service.dto.request.DiaryPostRequest;
import com.travel.diary_service.dto.response.DiaryPostResponse;
import com.travel.diary_service.entity.DiaryPostDocument;
import com.travel.diary_service.entity.DiaryPostEntity;
import com.travel.diary_service.entity.PhotoEntity;
import com.travel.diary_service.entity.TagEntity;
import com.travel.diary_service.mappers.DiaryPostMapper;
import com.travel.diary_service.repository.DiaryPostSearchRepository;
import com.travel.diary_service.repository.PostRepository;
import com.travel.diary_service.repository.SavedPostRepository;
import com.travel.diary_service.entity.SavedPostEntity;
import com.travel.diary_service.repository.TagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final DiaryPostMapper postMapper;
    private final DiaryPostSearchRepository searchRepository;
    private final SavedPostRepository savedPostRepository;


    @Transactional
    public DiaryPostResponse createPost(DiaryPostRequest request) {
        DiaryPostEntity post = postMapper.toEntity(request);
        addPhotos(post, request);
        addTags(post, request);
        DiaryPostEntity saved = postRepository.save(post);
        syncToElasticsearch(saved);
        return postMapper.toResponse(saved);
    }
    public List<DiaryPostResponse> getPublicFeed() {
        return postRepository.findByPublicPostTrueOrderByCreatedAtDesc().stream().map(postMapper::toResponse).collect(Collectors.toList());
    }
    public List<DiaryPostResponse> getUserPosts(Long userId) {
        return postRepository.findByUserIdOrderByCreatedAtDesc(userId).stream().map(postMapper::toResponse).collect(Collectors.toList());
    }

    public DiaryPostResponse getPostById(Long postId, Long userId) {
        var post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getPublicPost()) {
            if (userId == null || !post.getUserId().equals(userId)) {
                throw new RuntimeException("Forbidden: Этот пост приватный и доступен только автору");
            }
        }

        return postMapper.toResponse(post);
    }
    @Transactional
    public DiaryPostResponse updatePost(Long postId, Long userId, DiaryPostRequest request) {
        DiaryPostEntity post = getPostAndCheckOwnership(postId, userId);
        postMapper.updateEntityFromRequest(request, post);
        updatePhotos(post, request);
        addTags(post, request);
        DiaryPostEntity saved = postRepository.saveAndFlush(post);
        syncToElasticsearch(saved);
        return postMapper.toResponse(saved);
    }
    public void deletePost(Long postId, Long userId) {
        DiaryPostEntity post = getPostAndCheckOwnership(postId, userId);
        postRepository.delete(post);
        searchRepository.deleteById(postId.toString());

    }
    public List<DiaryPostResponse> searchPosts(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return getPublicFeed();
        }

        List<DiaryPostDocument> searchResults = searchRepository.searchPublicPosts(keyword);

        List<Long> postIds = searchResults.stream()
                .map(doc -> Long.valueOf(doc.getId()))
                .collect(Collectors.toList());

        if (postIds.isEmpty()) {
            return new ArrayList<>();
        }

        List<DiaryPostEntity> postsFromDb = postRepository.findAllByIdIn(postIds);

        return postsFromDb.stream()
                .map(postMapper::toResponse)
                .collect(Collectors.toList());
    }

    private void syncToElasticsearch(DiaryPostEntity post) {
        List<String> tagNames = new ArrayList<>();
        if (post.getTags() != null) {
            tagNames = post.getTags().stream().map(TagEntity::getName).collect(Collectors.toList());
        }

        DiaryPostDocument document = DiaryPostDocument.builder()
                .id(post.getId().toString())
                .title(post.getTitle())
                .location(post.getLocation())
                .tags(tagNames)
                .publicPost(post.getPublicPost())
                .build();

        searchRepository.save(document);
    }
    @Transactional
    public void savePostToBookmarks(Long postId, Long userId) {
        DiaryPostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getPublicPost() && !post.getUserId().equals(userId)) {
            throw new RuntimeException("Нельзя сохранить чужой приватный пост");
        }

        if (savedPostRepository.existsByUserIdAndPostId(userId, postId)) {
            throw new RuntimeException("Пост уже в сохраненных");
        }

        SavedPostEntity savedPost = new SavedPostEntity();
        savedPost.setUserId(userId);
        savedPost.setPost(post);
        savedPostRepository.save(savedPost);
    }

    @Transactional
    public void removePostFromBookmarks(Long postId, Long userId) {
        SavedPostEntity savedPost = savedPostRepository.findByUserIdAndPostId(userId, postId)
                .orElseThrow(() -> new RuntimeException("Пост не найден в закладках"));

        savedPostRepository.delete(savedPost);
    }

    public List<DiaryPostResponse> getBookmarkedPosts(Long userId) {
        List<SavedPostEntity> savedPosts = savedPostRepository.findByUserIdOrderBySavedAtDesc(userId);

        return savedPosts.stream()
                .map(saved -> postMapper.toResponse(saved.getPost()))
                .collect(Collectors.toList());
    }
    @Transactional
    public void deletePhoto(Long postId, Long photoId, Long userId) {
        DiaryPostEntity post = getPostAndCheckOwnership(postId, userId);
        post.getPhotos().removeIf(photoEntity -> photoEntity.getId().equals(photoId));

    }

    private void addPhotos(DiaryPostEntity post, DiaryPostRequest request) {

        if (request.getPhotos() == null) return;
        List<PhotoEntity> photoEntities = new ArrayList<>();
        for (String url : request.getPhotos()) {

            PhotoEntity photoEntity = new PhotoEntity();
            photoEntity.setUrl(url);
            photoEntity.setDiaryPost(post);
            photoEntities.add(photoEntity);
        }

        post.setPhotos(photoEntities);
    }
    private void updatePhotos(DiaryPostEntity post, DiaryPostRequest request) {

        if (request.getPhotos() == null) return;
        post.getPhotos().clear();
        List<PhotoEntity> photoEntities = new ArrayList<>();
        for (String url : request.getPhotos()) {

            PhotoEntity photoEntity = new PhotoEntity();
            photoEntity.setUrl(url);
            photoEntity.setDiaryPost(post);
            photoEntities.add(photoEntity);
        }
        post.getPhotos().addAll(photoEntities);
    }

    private void addTags(DiaryPostEntity post, DiaryPostRequest request) {

        if (request.getTags() == null) return;

        List<TagEntity> tagsFromDb = new ArrayList<>();

        for (String tagName : request.getTags()) {

            TagEntity existingTagEntity = tagRepository
                    .findByName(tagName)
                    .orElseGet(() -> tagRepository.save(new TagEntity(tagName)));
            tagsFromDb.add(existingTagEntity);
        }

        post.setTags(tagsFromDb);
    }
    private DiaryPostEntity getPostAndCheckOwnership(Long postId, Long userId) {
        DiaryPostEntity post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        if (!post.getUserId().equals(userId)) {
            throw new RuntimeException("You are not the owner of this post");
        }
        return post;
    }

}
