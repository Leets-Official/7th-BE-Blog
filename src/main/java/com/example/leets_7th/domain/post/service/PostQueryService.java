package com.example.leets_7th.domain.post.service;

import com.example.leets_7th.domain.post.dto.response.GetPostDetailResponse;
import com.example.leets_7th.domain.post.dto.response.GetPostResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PostQueryService {

    public GetPostResponse getAllPost(int page, int size){
        return null;
    }

    public GetPostDetailResponse getPostDetail(Long postId){
        return null;
    }
}
