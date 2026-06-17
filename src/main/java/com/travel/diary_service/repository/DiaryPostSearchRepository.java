package com.travel.diary_service.repository;

import com.travel.diary_service.entity.DiaryPostDocument;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface DiaryPostSearchRepository extends ElasticsearchRepository<DiaryPostDocument, String> {

    @Query("{\"bool\": {\"must\": [{\"term\": {\"publicPost\": true}}, {\"multi_match\": {\"query\": \"?0\", \"fields\": [\"title\", \"location\", \"tags\"]}}]}}")
    List<DiaryPostDocument> searchPublicPosts(String keyword);
}