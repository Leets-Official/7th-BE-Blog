package com.example.springbootassignment.service;

import com.example.springbootassignment.domain.post.entity.Post;
import com.example.springbootassignment.domain.post.entity.PostRepository;
import com.example.springbootassignment.dto.PostCreateRequest;
import com.example.springbootassignment.dto.PostListResponse;
import com.example.springbootassignment.dto.PostResponse;
import com.example.springbootassignment.dto.PostUpdateRequest;
import com.example.springbootassignment.exception.PostNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public PostListResponse getPostList(int page, int size, String sort, String order) {
        int pageNumber = Math.max(0, page - 1);
        Sort.Direction direction = "desc".equalsIgnoreCase(order) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(direction, sort));
        Page<Post> posts = postRepository.findAll(pageable);
        return PostListResponse.from(posts);
    }

    public PostResponse getPostDetail(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
        post.increaseViewCount();
        postRepository.save(post);
        return PostResponse.from(post);
    }

    public PostResponse createPost(PostCreateRequest request) {
        if (request.getTitle() == null || request.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("제목은 필수입니다.");
        }
        if (request.getContent() == null || request.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("내용은 필수입니다.");
        }

        Post post = new Post();
        post.setTitle(request.getTitle());
        post.setContent(request.getContent());

        Post savedPost = postRepository.save(post);
        return PostResponse.from(savedPost);
    }

    public PostResponse updatePost(Long postId, PostUpdateRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));

        if (request.getTitle() == null || request.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("제목은 필수입니다.");
        }
        if (request.getContent() == null || request.getContent().trim().isEmpty()) {
            throw new IllegalArgumentException("내용은 필수입니다.");
        }

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());

        Post updatedPost = postRepository.save(post);
        return PostResponse.from(updatedPost);
    }

    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException(postId));
        postRepository.delete(post);
    }
}
