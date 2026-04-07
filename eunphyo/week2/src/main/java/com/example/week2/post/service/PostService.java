package com.example.week2.post.service;

import com.example.week2.post.dto.PostCreateRequest;
import com.example.week2.post.dto.PostResponse;
import com.example.week2.post.dto.PostUpdateRequest;
import com.example.week2.post.entity.Post;
import com.example.week2.post.repository.PostRepository;
import com.example.week2.user.entity.User;
import com.example.week2.user.repository.UserRepository;
import com.example.week2.global.exception.ForbiddenPostAccessException;
import com.example.week2.global.exception.PostNotFoundException;
import com.example.week2.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostResponse.CreatePostResponse createPost(Long userId, PostCreateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .user(user)
                .build();

        Post savedPost = postRepository.save(post);

        return PostResponse.CreatePostResponse.builder()
                .postId(savedPost.getId())
                .title(savedPost.getTitle())
                .content(savedPost.getContent())
                .nickname(savedPost.getUser().getName())
                .createdAt(savedPost.getCreatedAt())
                .updatedAt(savedPost.getUpdatedAt())
                .build();
    }

    @Transactional
    public PostResponse.PostDetailResponse updatePost(Long userId, Long postId, PostUpdateRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        if (!post.getUser().getId().equals(userId)) {
            throw new ForbiddenPostAccessException();
        }

        post.update(request.getTitle(), request.getContent());

        return PostResponse.PostDetailResponse.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .nickname(post.getUser().getName())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    @Transactional
    public void deletePost(Long userId, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        if (!post.getUser().getId().equals(userId)) {
            throw new ForbiddenPostAccessException();
        }

        postRepository.delete(post);
    }

    public PostResponse.PostDetailResponse getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(PostNotFoundException::new);

        return PostResponse.PostDetailResponse.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .nickname(post.getUser().getName())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    public List<PostResponse.PostListResponse> getPosts() {
        return postRepository.findAll()
                .stream()
                .map(post -> PostResponse.PostListResponse.builder()
                        .postId(post.getId())
                        .title(post.getTitle())
                        .nickname(post.getUser().getName())
                        .createdAt(post.getCreatedAt())
                        .updatedAt(post.getUpdatedAt())
                        .build())
                .toList();
    }
}