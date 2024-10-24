CREATE TABLE `friendships_requests` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `id_user_request` bigint NOT NULL,
    `id_user_received` bigint NOT NULL,
    `status` enum('PENDING','ACCEPTED','DECLINED','BLOCKED') NOT NULL DEFAULT 'PENDING',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    UNIQUE KEY `unique_request` (`id_user_request`, `id_user_received`),
    FOREIGN KEY (`id_user_request`) REFERENCES `users`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`id_user_received`) REFERENCES `users`(`id`) ON DELETE CASCADE
)