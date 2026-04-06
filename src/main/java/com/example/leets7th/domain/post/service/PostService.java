package com.example.leets7th.domain.post.service;

import com.example.leets7th.domain.post.dto.res.PostResponseDTO;
import com.example.leets7th.domain.post.entity.Post;
import com.example.leets7th.domain.post.repository.PostRepository;
import com.example.leets7th.domain.user.entity.User;
import com.example.leets7th.domain.user.repository.UserRepository;
import com.example.leets7th.global.apiPayload.code.GeneralErrorCode;
import com.example.leets7th.global.apiPayload.exception.GeneralException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<PostResponseDTO.PostListResDTO> getPostList(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(GeneralErrorCode.UNAUTHORIZED));

        List<Post> postList = postRepository.findAll();

        return postList.stream()
                .map(post -> PostResponseDTO.PostListResDTO.builder()
                        .postId(post.getId())
                        .title(post.getTitle())
                        .nickname(post.getUser().getNickname())
                        .build())
                .toList();
    }
}
