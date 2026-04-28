package com.leets.blog.service;

import com.leets.blog.dto.*;
import com.leets.blog.entity.Post;
import com.leets.blog.entity.User;
import com.leets.blog.repository.PostRepository;
import com.leets.blog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Long createPost(PostCreateRequest request) {
        // 1. 유저 조회
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));

        // 2. 게시글 생성
        Post post = Post.builder()
                .title(request.title())
                .content(request.content())
                .user(user)
                .createdAt(LocalDateTime.now()) // <--- 직접 주입해서 NULL 에러 방지
                .updatedAt(LocalDateTime.now()) // <--- 직접 주입
                .build();

        return postRepository.save(post).getId();
    }

    public PostListResponse getPosts() {
        List<Post> posts = postRepository.findAllByDeletedAtIsNullOrderByCreatedAtDesc();

        List<PostListResponse.PostItem> items = posts.stream()
                .map(p -> new PostListResponse.PostItem(
                        p.getId(),
                        p.getTitle(),
                        p.getCreatedAt().toString(),
                        p.getUser().getId()
                ))
                .toList();

        return new PostListResponse(items, items.size(), 1);
    }

    public PostDetailResponse getPost(Long id) {
        Post post = postRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        return new PostDetailResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getUser().getNickname(),
                post.getCreatedAt().toString()
        );
    }

    @Transactional
    public void updatePost(Long id, PostUpdateRequest request) {
        Post post = postRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));

        // 수정 시에도 updatedAt 갱신이 필요하다면 여기서 세팅 가능
        post.update(request.title(), request.content());
    }

    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        post.delete();
    }
}