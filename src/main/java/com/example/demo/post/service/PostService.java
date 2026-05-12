package com.example.demo.post.service;

import com.example.demo.media.entity.Media;
import com.example.demo.media.repository.MediaRepository;
import com.example.demo.post.dto.*;
import com.example.demo.post.entity.Post;
import com.example.demo.post.entity.PostBlock;
import com.example.demo.post.repository.PostRepository;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final MediaRepository mediaRepository;

    // 게시글 생성
    @Transactional
    public PostResponse createPost(Long userId, PostCreateRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저 없음"));

        Post post = new Post();
        post.setUser(user);
        post.setTitle(request.getTitle());
        post.setDescription(request.getDescription());
        post.setCreatedAt(LocalDateTime.now());

        int order = 0;

        for (PostBlockDto dto : request.getBlocks()) {

            Media media = null;

            if (dto.getMediaId() != null) {
                media = mediaRepository.findById(dto.getMediaId())
                        .orElseThrow(() -> new IllegalArgumentException("이미지 없음"));
            }

            PostBlock block = PostBlock.builder()
                    .blockType(dto.getBlockType())
                    .sortOrder(order++)
                    .textContent(dto.getTextContent())
                    .media(media)
                    .createdAt(LocalDateTime.now())
                    .build();

            post.addBlock(block);
        }

        Post saved = postRepository.save(post);

        return PostResponse.builder()
                .postId(saved.getId())
                .title(saved.getTitle())
                .description(saved.getDescription())
                .authorNickname(user.getNickname())
                .createdAt(saved.getCreatedAt())
                .build();
    }

    // 게시글 수정
    @Transactional
    public PostResponse updatePost(Long userId, Long postId, PostCreateRequest request) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        validatePostOwner(post, userId);

        post.setTitle(request.getTitle());
        post.setDescription(request.getDescription());
        post.setUpdatedAt(LocalDateTime.now());

        return PostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .description(post.getDescription())
                .authorNickname(post.getUser().getNickname())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    // 게시글 삭제
    @Transactional
    public Long deletePost(Long userId, Long postId) {

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        validatePostOwner(post, userId);

        postRepository.delete(post);

        return postId;
    }

    // 게시글 조회
    @Transactional
    public PostDetailResponse getPostDetail(Long postId) {

        Post post = postRepository.findPostWithBlocks(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글 없음"));

        List<PostBlockResponse> blocks = post.getBlocks().stream()
                .sorted(Comparator.comparingInt(PostBlock::getSortOrder))
                .map(block -> PostBlockResponse.builder()
                        .blockType(block.getBlockType())
                        .textContent(block.getTextContent())
                        .imageUrl(block.getMedia() != null ? block.getMedia().getUrl() : null)
                        .build())
                .toList();

        return PostDetailResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .description(post.getDescription())
                .authorNickname(post.getUser().getNickname())
                .createdAt(post.getCreatedAt())
                .blocks(blocks)
                .build();
    }

    private void validatePostOwner(Post post, Long userId) {
        if (!post.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("게시글 작성자만 수정 또는 삭제할 수 있습니다.");
        }
    }
}
