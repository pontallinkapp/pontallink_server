ALTER TABLE `friendships_requests`
    ADD UNIQUE KEY `unique_user_request_received` (`id_user_request`, `id_user_received`);
