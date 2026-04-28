INSERT IGNORE INTO users (id, age, name, email, nickname, created_at, updated_at)
VALUES (1, 24, '조연준', 'yeonjun@example.com', 'yeonjun', NOW(), NOW());

INSERT IGNORE INTO post (id, user_id, title, content, description, created_at, updated_at, deleted_at, active)
VALUES (1, 1, '첫 게시글', '내용입니다.', '설명입니다.', NOW(), NOW(), NULL, TRUE);

INSERT IGNORE INTO post (id, user_id, title, content, description, created_at, updated_at, deleted_at, active)
VALUES (2, 1, '두 번째 게시글', '두 번째 내용입니다.', '두 번째 설명입니다.', NOW(), NOW(), NULL, TRUE);