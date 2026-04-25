package com.leets.blog.domain.post.service;

import com.leets.blog.domain.post.converter.PostConverter;
import com.leets.blog.domain.post.dto.PostRequest;
import com.leets.blog.domain.post.dto.PostResponse;
import com.leets.blog.domain.post.entity.Post;
import com.leets.blog.domain.user.entity.User;
import com.leets.blog.domain.post.repository.PostRepository;
import com.leets.blog.domain.user.repository.UserRepository;
import com.leets.blog.global.common.BaseErrorCode;
import com.leets.blog.global.exception.PostException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 1. 게시글 작성
    @Transactional
    public PostResponse createPost(PostRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new PostException(BaseErrorCode.USER_NOT_FOUND)); 
        
        Post post = PostConverter.toPost(request, user);
        return PostConverter.toPostResponse(postRepository.save(post));
    }

    // 2. 게시글 목록 조회
    public List<PostResponse> getPostList() {
        return postRepository.findAllByIsDeletedFalse().stream()
                .map(PostConverter::toPostResponse)
                .collect(Collectors.toList());
    }

    // 3. 게시글 상세 조회
    public PostResponse getPostDetail(Long id) {
        Post post = postRepository.findById(id)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new PostException(BaseErrorCode.POST_NOT_FOUND));
        
        return PostConverter.toPostResponse(post);
    }

    // 4. 게시글 삭제 (Soft Delete)
    @Transactional
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new PostException(BaseErrorCode.POST_NOT_FOUND));
        
        // Soft delete 로직 추가 가능
    }

    // 5. 게시글 수정 (PATCH)
    @Transactional
    public PostResponse updatePost(Long id, PostRequest request) {
        Post post = postRepository.findById(id)
                .filter(p -> !p.isDeleted())
                .orElseThrow(() -> new PostException(BaseErrorCode.POST_NOT_FOUND));

        post.update(request.getTitle(), request.getContent());
        return PostConverter.toPostResponse(post);
    }
}
