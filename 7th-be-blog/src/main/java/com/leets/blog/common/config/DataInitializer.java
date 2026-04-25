package com.leets.blog.common.config;

import com.leets.blog.domain.user.entity.User;
import com.leets.blog.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserRepository userRepository;

    @Bean
    public CommandLineRunner initData() {
        return args -> {
            if (userRepository.count() > 0) {
                return;
            }

            User user = User.builder()
                    .name("tester")
                    .email("tester@test.com")
                    .password("1234")
                    .build();

            userRepository.save(user);
            log.info("Initialized default user. id={}", user.getId());
        };
    }
}
