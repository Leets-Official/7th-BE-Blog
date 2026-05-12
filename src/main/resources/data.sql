INSERT INTO users (id, age, name, email, nickname, password, role, provider, created_at, updated_at)
VALUES (100, 24, 'sample user', 'sample@example.com', 'sample-user', '$2a$10$2bMWiPi4yWjY/dYpY2hTneTOwp9LbWgU5nfCNLqYC9Ub5PTJT1KdS', 'USER', 'LOCAL', NOW(), NOW());

INSERT INTO post (id, user_id, title, content, description, created_at, updated_at, deleted_at, active)
VALUES (100, 100, 'sample post', 'sample content', 'sample description', NOW(), NOW(), NULL, TRUE);

INSERT INTO post (id, user_id, title, content, description, created_at, updated_at, deleted_at, active)
VALUES (101, 100, 'second sample post', 'second sample content', 'second sample description', NOW(), NOW(), NULL, TRUE);
