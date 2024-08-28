package com.pontallink_server.pontallink.services;

import com.pontallink_server.pontallink.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pontallink_server.pontallink.dtos.UserProfileDTO;
import com.pontallink_server.pontallink.repositories.UserRepository;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private User user;

    public UserProfileDTO getUserProfile(String username) throws EntityNotFoundException {
        return new UserProfileDTO((User) userRepository.findByLogin(username));
    }

    public List<UserProfileDTO> getListFriendships(Long userId) throws EntityNotFoundException {
         List<User> friends = userRepository.findFriendsByUserId(userId);

         return friends.stream().map(UserProfileDTO::new).collect(Collectors.toList());
         //return friends.stream().map(friend -> new UserProfileDTO(friend.getId(), friend.getName(), friend.getBio(), List.of("All"), friend.getCondominium(), friend.getUserProfileImageMid())).collect(Collectors.toList());
    }

    public UserProfileDTO searchUserId(Long userId) throws EntityNotFoundException {
        return userRepository.findById(userId).map(UserProfileDTO::new).orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado!"));
    }

    public List<UserProfileDTO> getUsers(){
        //return userRepository.findAll().stream().map(UserProfileDTO::new).collect(Collectors.toList());
        return userRepository.findAll().stream().map(UserProfileDTO::new).collect(Collectors.toList());
    }
}
