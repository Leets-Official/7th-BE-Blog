package com.example.leets_exercise1.service;

import com.example.leets_exercise1.dto.post.request.PostCreateRequest;
import com.example.leets_exercise1.dto.post.request.PostUpdateRequest;
import com.example.leets_exercise1.dto.post.response.PostCreateResponse;
import com.example.leets_exercise1.dto.post.response.PostDeleteResponse;
import com.example.leets_exercise1.dto.post.response.PostDetailResponse;
import com.example.leets_exercise1.dto.post.response.PostListResult;

public interface PostService {

    PostListResult getPosts(int page, int size);

    PostDetailResponse getPost(Long postId);

    PostCreateResponse createPost(PostCreateRequest request);

    PostDetailResponse updatePost(Long postId, PostUpdateRequest request);

    PostDeleteResponse deletePost(Long postId);
}