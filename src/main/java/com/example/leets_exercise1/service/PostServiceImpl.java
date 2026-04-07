package com.example.leets_exercise1.service;

import com.example.leets_exercise1.domain.post.Post;
import com.example.leets_exercise1.domain.user.User;
import com.example.leets_exercise1.dto.post.request.PostCreateRequest;
import com.example.leets_exercise1.dto.post.request.PostUpdateRequest;
import com.example.leets_exercise1.dto.post.response.PostCreateResponse;
import com.example.leets_exercise1.dto.post.response.PostDeleteResponse;
import com.example.leets_exercise1.dto.post.response.PostDetailResponse;
import com.example.leets_exercise1.dto.post.response.PostListResult;
import com.example.leets_exercise1.dto.post.response.PostSummaryResponse;
import com.example.leets_exercise1.exception.PostNotFoundException;
import com.example.leets_exercise1.exception.UserNotFoundException;
import com.example.leets_exercise1.repository.PostRepository;
import com.example.leets_exercise1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public PostListResult getPosts(int page, int size) {
        return new PostListResult(
                postRepository.findAllByDeletedAtIsNullAndActiveTrue(
                                PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt"))
                        )
                        .stream()
                        .map(post -> new PostSummaryResponse(
                                post.getId(),
                                post.getTitle(),
                                post.getDescription(),
                                post.getUser().getNickname(),
                                post.getCreatedAt()
                        ))
                        .toList()
        );
    }

    @Override
    public PostDetailResponse getPost(Long postId) {
        Post post = postRepository.findByIdAndDeletedAtIsNullAndActiveTrue(postId)
                .orElseThrow(PostNotFoundException::new);

        return new PostDetailResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getDescription(),
                post.getUser().getNickname(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }

    @Override
    @Transactional
    public PostCreateResponse createPost(PostCreateRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(UserNotFoundException::new);

        Post post = Post.builder()
                .user(user)
                .title(request.getTitle())
                .content(request.getContent())
                .description(request.getDescription())
                .active(true)
                .build();

        Post savedPost = postRepository.save(post);

        return new PostCreateResponse(
                savedPost.getId(),
                savedPost.getTitle(),
                savedPost.getUser().getNickname(),
                savedPost.getCreatedAt()
        );
    }

    @Override
    @Transactional
    public PostDetailResponse updatePost(Long postId, PostUpdateRequest request) {
        Post post = postRepository.findByIdAndDeletedAtIsNullAndActiveTrue(postId)
                .orElseThrow(PostNotFoundException::new);

        post.update(
                request.getTitle(),
                request.getContent(),
                request.getDescription()
        );

        return new PostDetailResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getDescription(),
                post.getUser().getNickname(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }

    @Override
    @Transactional
    public PostDeleteResponse deletePost(Long postId) {
        Post post = postRepository.findByIdAndDeletedAtIsNullAndActiveTrue(postId)
                .orElseThrow(PostNotFoundException::new);

        post.softDelete();

        return new PostDeleteResponse(post.getId());
    }
}