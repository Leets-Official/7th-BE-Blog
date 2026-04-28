package com.leets.blog.post.service;

import com.leets.blog.global.exception.ErrorCode; // 추가
import com.leets.blog.post.domain.Post;
import com.leets.blog.post.domain.PostStatus; // 추가
import com.leets.blog.post.repository.PostRepository;
import com.leets.blog.post.dto.PostRequest;
import com.leets.blog.post.dto.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostResponse create(PostRequest.Create request) {
        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .status(PostStatus.ACTIVE)
                .build();

        Post savedPost = postRepository.save(post);
        return new PostResponse(savedPost);
    }

    // 단건 조회
    public PostResponse findById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.POST_NOT_FOUND.getMessage()));
        return new PostResponse(post);
    }

    // 전체 조회
    public List<PostResponse> findAll() {
        return postRepository.findAll().stream()
                .map(PostResponse::new)
                .toList();
    }

    @Transactional
    public PostResponse update(Long id, PostRequest.Update request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.POST_NOT_FOUND.getMessage()));

        // 엔티티 내부의 update 메서드를 통해 값 변경 (더티 체킹 발생)
        post.update(request.getTitle(), request.getContent());

        return new PostResponse(post);
    }

    @Transactional
    public void delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.POST_NOT_FOUND.getMessage()));

        postRepository.delete(post);
    }

    @Transactional
    public PostResponse hide(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.POST_NOT_FOUND.getMessage()));
        post.hide();
        return new PostResponse(post);
    }

    @Transactional
    public PostResponse activate(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(ErrorCode.POST_NOT_FOUND.getMessage()));
        post.activate();
        return new PostResponse(post);
    }
}