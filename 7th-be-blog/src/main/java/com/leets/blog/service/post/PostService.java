package com.leets.blog.service.post;

import com.leets.blog.converter.post.PostConverter;
import com.leets.blog.dto.post.PostRequest;
import com.leets.blog.dto.post.PostResponse;
import com.leets.blog.entity.post.Post;
import com.leets.blog.entity.user.User;
import com.leets.blog.repository.PostRepository;
import com.leets.blog.repository.UserRepository;
import com.leets.blog.common.BaseErrorCode;
import com.leets.blog.exception.PostException;
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
}
