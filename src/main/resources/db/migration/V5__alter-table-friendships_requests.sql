ALTER TABLE `friendships_requests`
DROP INDEX `unique_user_request_received`,
ADD UNIQUE KEY `unique_user_request_received_status` (`id_user_request`, `id_user_received`, `status`);
