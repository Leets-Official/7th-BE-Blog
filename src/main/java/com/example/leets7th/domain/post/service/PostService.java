package com.example.leets7th.domain.post.service;

import com.example.leets7th.domain.post.dto.req.PostRequestDTO;
import com.example.leets7th.domain.post.dto.res.PostResponseDTO;
import com.example.leets7th.domain.post.entity.Post;
import com.example.leets7th.domain.post.exception.PostException;
import com.example.leets7th.domain.post.exception.code.PostErrorCode;
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
                        .createdAt(post.getCreatedAt())
                        .updatedAt(post.getUpdatedAt())
                        .build())
                .toList();
    }

    @Transactional
    public PostResponseDTO.PostDetailResDTO getPostDetail(Long postId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(GeneralErrorCode.UNAUTHORIZED));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND));

        return PostResponseDTO.PostDetailResDTO.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .nickname(post.getUser().getNickname())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    @Transactional
    public PostResponseDTO.CreatePostResDTO createPost(Long userId, PostRequestDTO.PostReqDTO req) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(GeneralErrorCode.UNAUTHORIZED));

        Post post = Post.builder()
                .title(req.title())
                .content(req.content())
                .user(user)
                .build();

        Post savedPost = postRepository.save(post);

        return PostResponseDTO.CreatePostResDTO.builder()
                .postId(savedPost.getId())
                .title(savedPost.getTitle())
                .content(savedPost.getContent())
                .nickname(savedPost.getUser().getNickname())
                .createdAt(savedPost.getCreatedAt())
                .updatedAt(savedPost.getUpdatedAt())
                .build();
    }

    @Transactional
    public PostResponseDTO.PostDetailResDTO patchPost(Long postId, Long userId, PostRequestDTO.PostReqDTO req) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(GeneralErrorCode.UNAUTHORIZED));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND));

        if (!post.getUser().getId().equals(userId)) {
            throw new PostException(PostErrorCode.POST_FORBIDDEN);
        }

        post.update(req.title(), req.content());

        return PostResponseDTO.PostDetailResDTO.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .nickname(post.getUser().getNickname())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}
