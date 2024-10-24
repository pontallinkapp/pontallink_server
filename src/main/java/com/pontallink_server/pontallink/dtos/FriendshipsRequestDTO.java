package com.pontallink_server.pontallink.dtos;

import com.pontallink_server.pontallink.entities.FriendshipsRequest;

import java.time.LocalDateTime;

public record FriendshipsRequestDTO(Long senderId, Long receiverId, String status, LocalDateTime createdAt) {

    public FriendshipsRequestDTO(FriendshipsRequest friendshipsRequest){
        this(friendshipsRequest.getSender().getId(), friendshipsRequest.getReceiver().getId(), friendshipsRequest.getStatus().name(), friendshipsRequest.getCreatedAt());
    }
}
