package com.pontallink_server.pontallink.services;

import com.pontallink_server.pontallink.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pontallink_server.pontallink.dtos.UserProfileDTO;
import com.pontallink_server.pontallink.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public UserProfileDTO getUserProfile(String username) throws EntityNotFoundException {
        return new UserProfileDTO((User) repository.findByLogin(username));
    }
}
