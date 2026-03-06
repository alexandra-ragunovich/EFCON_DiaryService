package com.travel.diary_service.service;

import com.travel.diary_service.entity.DiaryPost;
import com.travel.diary_service.entity.Photo;
import com.travel.diary_service.entity.Tag;
import com.travel.diary_service.repository.PostRepository;
import com.travel.diary_service.repository.TagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    @Transactional
    public DiaryPost createPost(DiaryPost post) {
        if (post.getPhotos() != null) {
            for (Photo photo : post.getPhotos()) {
                photo.setDiaryPost(post);
            }
        }
        if (post.getTags() != null) {

            List<Tag> tagsFromDb = new ArrayList<>();

            for (Tag tag : post.getTags()) {

                Tag existingTag = tagRepository
                        .findByName(tag.getName())
                        .orElseGet(() -> tagRepository.save(tag));

                tagsFromDb.add(existingTag);
            }

            post.setTags(tagsFromDb);
        }

        return postRepository.save(post);
    }
    public List<DiaryPost> getPublicFeed() {
        return postRepository.findByPublicPostTrueOrderByCreatedAtDesc();
    }
    public List<DiaryPost> getUserPosts(Long userId) {
        return postRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }
    public DiaryPost updatePost(Long postId, Long userId, DiaryPost updatedPost) {

        DiaryPost post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getUserId().equals(userId)) {
            throw new RuntimeException("You cannot edit this post");
        }

        if (updatedPost.getTitle() != null) {
            post.setTitle(updatedPost.getTitle());
        }

        if (updatedPost.getContent() != null) {
            post.setContent(updatedPost.getContent());
        }

        if (updatedPost.getLocation() != null) {
            post.setLocation(updatedPost.getLocation());
        }

        if (updatedPost.getDate() != null) {
            post.setDate(updatedPost.getDate());
        }

        if (updatedPost.getRating() != null) {
            post.setRating(updatedPost.getRating());
        }

        if (updatedPost.getDuration() != null) {
            post.setDuration(updatedPost.getDuration());
        }

        if (updatedPost.getTransportInfo() != null) {
            post.setTransportInfo(updatedPost.getTransportInfo());
        }

        if (updatedPost.getExpenses() != null) {
            post.setExpenses(updatedPost.getExpenses());
        }

        if (updatedPost.getTips() != null) {
            post.setTips(updatedPost.getTips());
        }
        if (updatedPost.getPublicPost() != null) {
            post.setPublicPost(updatedPost.getPublicPost());
        }

        return postRepository.save(post);
    }
    public void deletePost(Long postId, Long userId) {
        DiaryPost post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));
        if (!post.getUserId().equals(userId)) {
            throw new RuntimeException("You cannot delete this post");
        }
        postRepository.delete(post);
    }
    @Transactional
    public void deletePhoto(Long postId, Long photoId, Long userId) {
        DiaryPost post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (!post.getUserId().equals(userId)) {
            throw new RuntimeException("You cannot delete photo from this post");
        }

        post.getPhotos().removeIf(photo -> photo.getId().equals(photoId));

    }


}
