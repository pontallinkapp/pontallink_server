package com.pontallink_server.pontallink.dtos;

import com.pontallink_server.pontallink.entities.FriendshipsRequest;
import com.pontallink_server.pontallink.entities.User;

public record FriendshipsIdDTO(Long requestId, Long userId, String name, String userProfileImageMid) {

    public FriendshipsIdDTO(User user, FriendshipsRequest friendshipsRequest) {
        this(friendshipsRequest.getId(), user.getId(), user.getName(), user.getUserProfileImageMid());
    }
}
