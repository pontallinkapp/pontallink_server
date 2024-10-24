package com.pontallink_server.pontallink.dtos;

import java.util.List;

import com.pontallink_server.pontallink.entities.Condominium;
import org.springframework.security.core.userdetails.UserDetails;

import com.pontallink_server.pontallink.entities.User;

public record UserProfileDTO(Long id, String name, String bio, List<String> interests, Condominium condominium, String userProfileImageMid) {

	public UserProfileDTO(User user) {
        this(user.getId(), user.getName(), user.getBio(), List.of("All"), user.getCondominium(), user.getUserProfileImageMid());
    }

}
