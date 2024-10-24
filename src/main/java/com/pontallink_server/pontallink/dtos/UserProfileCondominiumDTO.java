package com.pontallink_server.pontallink.dtos;

import com.pontallink_server.pontallink.entities.Condominium;
import com.pontallink_server.pontallink.entities.User;

import java.util.List;

public record UserProfileCondominiumDTO(Long id, String name, String bio, String condominiumName, String userProfileImageMid, List<String> interests, Long friends) {
    public UserProfileCondominiumDTO(Long id, String name, String bio, String condominiumName, String userProfileImageMid, List<String> interests, Long friends) {
        this.id = id;
        this.name = name;
        this.bio = bio;
        this.condominiumName = condominiumName;
        this.userProfileImageMid = userProfileImageMid;
        this.interests = interests;
        this.friends = friends;
    }

    // Construtor que recebe Object[] e os interesses
    public UserProfileCondominiumDTO(Object[] data, List<String> interests, Long friends) {
        this((Long) data[0], (String) data[1], (String) data[2], (String) data[3], (String) data[4], interests, friends);
    }
}
