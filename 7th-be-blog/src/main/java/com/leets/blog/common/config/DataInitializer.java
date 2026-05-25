package com.leets.blog.common.config;

import com.leets.blog.domain.user.entity.User;
import com.leets.blog.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            if (userRepository.count() > 0) {
                return;
            }

            User user = User.builder()
                    .name("tester")
                    .nickname("tester")
                    .email("tester@test.com")
                    .password(passwordEncoder.encode("1234"))
                    .build();

            userRepository.save(user);
            log.info("Initialized default user. id={}", user.getId());
        };
    }
}
