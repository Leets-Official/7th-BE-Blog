package com.example.week2.post.service;

import com.example.week2.post.dto.PostCreateRequest;
import com.example.week2.post.dto.PostResponse;
import com.example.week2.post.entity.Post;
import com.example.week2.post.repository.PostRepository;
import com.example.week2.user.entity.User;
import com.example.week2.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.week2.global.response.CustomException;
import com.example.week2.global.response.ErrorCode;

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
                .orElseThrow(()-> new CustomException((ErrorCode.USER_NOT_FOUND)));

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
    public PostResponse.PostDetailResponse updatePost(Long userId, Long postId, PostCreateRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new CustomException(ErrorCode.POST_NOT_FOUND));

        if (!post.getUser().getId().equals(userId)) {
              throw new CustomException(ErrorCode.FORBIDDEN);
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
                .orElseThrow (()-> new CustomException(ErrorCode.POST_NOT_FOUND));

        if (!post.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorCode.FORBIDDEN);
        }

        postRepository.delete(post);
    }

    public PostResponse.PostDetailResponse getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new CustomException(ErrorCode.POST_NOT_FOUND));

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