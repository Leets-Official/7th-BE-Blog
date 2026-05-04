package com.leets.blog.post.service;

import com.leets.blog.global.exception.BusinessException;
import com.leets.blog.global.exception.ErrorCode;
import com.leets.blog.user.auth.AuthUser;
import com.leets.blog.post.domain.Post;
import com.leets.blog.post.domain.PostStatus; // 추가
import com.leets.blog.post.repository.PostRepository;
import com.leets.blog.post.dto.PostRequest;
import com.leets.blog.post.dto.PostResponse;
import com.leets.blog.user.domain.User;
import com.leets.blog.user.domain.UserRole;
import com.leets.blog.user.repository.UserRepository;
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
    public PostResponse create(AuthUser authUser, PostRequest.Create request) {
        User user = userRepository.findById(authUser.getUserId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Post post = Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .status(PostStatus.ACTIVE)
                .user(user)
                .build();

        Post savedPost = postRepository.save(post);
        return new PostResponse(savedPost);
    }

    // 단건 조회
    public PostResponse findById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));
        return new PostResponse(post);
    }

    // 전체 조회
    public List<PostResponse> findAll() {
        return postRepository.findAll().stream()
                .map(PostResponse::new)
                .toList();
    }

    @Transactional
    public PostResponse update(AuthUser authUser, Long id, PostRequest.Update request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));
        validatePostAuthority(authUser, post);

        // 엔티티 내부의 update 메서드를 통해 값 변경 (더티 체킹 발생)
        post.update(request.getTitle(), request.getContent());

        return new PostResponse(post);
    }

    @Transactional
    public void delete(AuthUser authUser, Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));
        validatePostAuthority(authUser, post);

        postRepository.delete(post);
    }

    @Transactional
    public PostResponse hide(AuthUser authUser, Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));
        validatePostAuthority(authUser, post);
        post.hide();
        return new PostResponse(post);
    }

    @Transactional
    public PostResponse activate(AuthUser authUser, Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.POST_NOT_FOUND));
        validatePostAuthority(authUser, post);
        post.activate();
        return new PostResponse(post);
    }

    private void validatePostAuthority(AuthUser authUser, Post post) {
        boolean isAuthor = post.getUser() != null && post.getUser().getId().equals(authUser.getUserId());
        boolean isAdmin = authUser.getRole() == UserRole.ADMIN;
        if (!isAuthor && !isAdmin) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }
    }
}
