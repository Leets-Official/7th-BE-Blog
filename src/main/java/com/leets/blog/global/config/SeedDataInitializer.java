package com.leets.blog.global.config;

import com.leets.blog.comment.domain.Comment;
import com.leets.blog.comment.repository.CommentRepository;
import com.leets.blog.post.domain.Post;
import com.leets.blog.post.domain.PostStatus;
import com.leets.blog.post.repository.PostRepository;
import com.leets.blog.report.domain.Report;
import com.leets.blog.report.domain.ReportTargetType;
import com.leets.blog.report.repository.ReportRepository;
import com.leets.blog.user.domain.User;
import com.leets.blog.user.domain.UserRole;
import com.leets.blog.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Profile("local")
@RequiredArgsConstructor
public class SeedDataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final ReportRepository reportRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        User admin = userRepository.findByEmail("admin@leets.com")
                .orElseGet(() -> userRepository.save(
                        new User("admin@leets.com", passwordEncoder.encode("admin1234"), "관리자", UserRole.ADMIN)
                ));

        User user1 = userRepository.findByEmail("user1@leets.com")
                .orElseGet(() -> userRepository.save(
                        new User("user1@leets.com", passwordEncoder.encode("user12345"), "유저1", UserRole.USER)
                ));

        User user2 = userRepository.findByEmail("user2@leets.com")
                .orElseGet(() -> userRepository.save(
                        new User("user2@leets.com", passwordEncoder.encode("user12345"), "유저2", UserRole.USER)
                ));

        if (postRepository.count() > 0) {
            return;
        }

        Post post1 = postRepository.save(Post.builder()
                .title("임시 게시물 1")
                .content("임시 데이터용 게시물입니다.")
                .status(PostStatus.ACTIVE)
                .user(user1)
                .build());

        Post post2 = postRepository.save(Post.builder()
                .title("임시 게시물 2")
                .content("신고/댓글 테스트용 게시물입니다.")
                .status(PostStatus.ACTIVE)
                .user(user2)
                .build());

        commentRepository.save(new Comment("첫 번째 임시 댓글입니다.", post1, user2));
        Comment comment2 = commentRepository.save(new Comment("두 번째 임시 댓글입니다.", post2, user1));

        if (!reportRepository.existsByReporterIdAndTargetTypeAndTargetId(user1.getId(), ReportTargetType.POST, post2.getId())) {
            reportRepository.save(new Report(ReportTargetType.POST, post2.getId(), "광고성 게시물입니다.", user1));
        }

        if (!reportRepository.existsByReporterIdAndTargetTypeAndTargetId(user2.getId(), ReportTargetType.COMMENT, comment2.getId())) {
            Report resolvedReport = new Report(ReportTargetType.COMMENT, comment2.getId(), "비방 표현이 있습니다.", user2);
            resolvedReport.resolve(admin);
            reportRepository.save(resolvedReport);
        }
    }
}
