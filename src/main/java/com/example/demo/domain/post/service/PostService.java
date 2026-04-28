package com.example.demo.domain.post.service;

import com.example.demo.domain.post.dto.*;
import com.example.demo.domain.post.entity.Post;
import com.example.demo.domain.post.repository.PostRepository;
import com.example.demo.domain.user.entity.User;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostListResponse getPosts(int page, int size) {
        Page<Post> postPage = postRepository.findAll(PageRequest.of(page, size));

        return new PostListResponse(
                postPage.getContent().stream()
                        .map(post -> new PostListItemResponse(
                                post.getId(),
                                post.getTitle(),
                                post.getContent(),
                                post.getImageUrl(),
                                post.getStatus(),
                                post.getUser().getName(),
                                post.getCreatedAt()
                        ))
                        .toList(),
                new PageInfoResponse(
                        postPage.getNumber(),
                        postPage.getSize(),
                        postPage.getTotalElements(),
                        postPage.getTotalPages(),
                        postPage.hasNext()
                )
        );
    }

    public PostDetailResponse getPost(Long postId) {
        Post post = findPost(postId);

        return new PostDetailResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getImageUrl(),
                post.getStatus(),
                post.getUser().getId(),
                post.getUser().getName(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }

    @Transactional
    public PostCreateResponse createPost(PostCreateRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", "해당 사용자를 찾을 수 없습니다."));

        Post post = Post.of(
                user,
                request.title(),
                request.content(),
                request.imageUrl()
        );

        Post savedPost = postRepository.save(post);
        return new PostCreateResponse(savedPost.getId(), "게시글이 생성되었습니다.");
    }

    @Transactional
    public void updatePost(Long postId, PostUpdateRequest request) {
        Post post = findPost(postId);

        validateOwner(post, request.userId());

        if ((request.title() == null || request.title().isBlank())
                && (request.content() == null || request.content().isBlank())
                && request.imageUrl() == null) {
            throw new CustomException(HttpStatus.BAD_REQUEST, "INVALID_PARAMETER", "수정할 값이 없습니다.");
        }

        post.update(request.title(), request.content(), request.imageUrl());
    }

    @Transactional
    public void deletePost(Long postId, PostDeleteRequest request) {
        Post post = findPost(postId);

        validateOwner(post, request.userId());

        postRepository.delete(post);
    }

    private Post findPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND, "POST_NOT_FOUND", "해당 게시글을 찾을 수 없습니다."));
    }

    private void validateOwner(Post post, Long userId) {
        if (!post.getUser().getId().equals(userId)) {
            throw new CustomException(HttpStatus.FORBIDDEN, "FORBIDDEN", "권한이 없습니다.");
        }
    }
}
