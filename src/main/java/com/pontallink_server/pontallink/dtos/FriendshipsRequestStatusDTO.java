package com.pontallink_server.pontallink.dtos;

import com.pontallink_server.pontallink.entities.FriendshipsRequest;

import java.time.LocalDateTime;

public record FriendshipsRequestStatusDTO(Long requestId, Long senderId, Long receiverId, String status, LocalDateTime requestDate) {

    public FriendshipsRequestStatusDTO(FriendshipsRequest friendshipsRequest){
        this(friendshipsRequest.getId(), friendshipsRequest.getSender().getId(), friendshipsRequest.getReceiver().getId(), friendshipsRequest.getStatus().name(), friendshipsRequest.getCreatedAt());
    }
}
