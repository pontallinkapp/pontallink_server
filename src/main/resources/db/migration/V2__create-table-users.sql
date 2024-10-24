CREATE TABLE `users` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `login` varchar(100) NOT NULL,
    `password` varchar(255) NOT NULL,
    `name` varchar(100) NOT NULL,
    `bio` varchar(255) DEFAULT NULL,
    `condominium_id` bigint,
    `user_profile_image_mid` longtext,
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `active` tinyint(1) NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`condominium_id`) REFERENCES `condominiums`(`id`) ON DELETE SET NULL
)