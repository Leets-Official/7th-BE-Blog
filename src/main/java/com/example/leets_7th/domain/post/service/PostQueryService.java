package com.example.leets_7th.domain.post.service;

import com.example.leets_7th.domain.post.dto.request.GetPostRequest;
import com.example.leets_7th.domain.post.dto.response.GetPostDetailResponse;
import com.example.leets_7th.domain.post.dto.response.GetPostResponse;
import com.example.leets_7th.domain.post.dto.response.PostSummary;
import com.example.leets_7th.domain.post.entity.Post;
import com.example.leets_7th.domain.post.repository.PostRepository;
import com.example.leets_7th.domain.post.validator.PostValidator;
import com.example.leets_7th.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostQueryService {

    private final PostRepository postRepository;
    private final PostValidator postValidator;

    public GetPostResponse getAllPost(GetPostRequest request) {

        User user = postValidator.validateUser(request.userId());

        Page<Post> postPage = postRepository
                .findAllByUserAndDeletedAtIsNull(user, request.toPageable());

        List<PostSummary> postList = postPage.getContent().stream()
                .map(PostSummary::from)
                .toList();

        return GetPostResponse.of(
                postList,
                postPage.getNumber() + 1,
                postPage.getSize(),
                postPage.getTotalElements(),
                postPage.getTotalPages()
        );
    }

    public GetPostDetailResponse getPostDetail(Long userId, Long postId) {

        postValidator.validateUser(userId);
        Post post = postValidator.validatePost(postId);

        return GetPostDetailResponse.from(post);
    }

}
