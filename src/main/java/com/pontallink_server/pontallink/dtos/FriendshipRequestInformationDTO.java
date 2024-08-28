package com.pontallink_server.pontallink.dtos;

import com.pontallink_server.pontallink.entities.FriendshipStatus;
import com.pontallink_server.pontallink.entities.FriendshipsRequest;

public record FriendshipRequestInformationDTO(Long idRequest, Long IdReceiver, String name, FriendshipStatus friendshipStatus) {

    public FriendshipRequestInformationDTO(FriendshipsRequest friendshipsRequest) {
        this(friendshipsRequest.getId(), friendshipsRequest.getReceiver().getId(), friendshipsRequest.getReceiver().getName() ,friendshipsRequest.getStatus());
        //this(user.getId(), user.getName(), user.getBio(), List.of("All"), user.getCondominium(), user.getUserProfileImageMid());
    }
}
