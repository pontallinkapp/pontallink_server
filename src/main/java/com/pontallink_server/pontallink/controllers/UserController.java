package com.pontallink_server.pontallink.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pontallink_server.pontallink.dtos.UserProfileDTO;
import com.pontallink_server.pontallink.infra.security.TokenService;
import com.pontallink_server.pontallink.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private TokenService tokenService;

	//Consulta usuario logado
	@GetMapping("/me")
	public ResponseEntity<UserProfileDTO> getUserProfile() {

		var userName = tokenService.getSubjectCurrentToken();
		var userProfile = userService.getUserProfile(userName);
		return ResponseEntity.ok(userProfile);

	}

	//Pegar lista de amigo do usuario logado
	@GetMapping("/friendships")
	public ResponseEntity<List<UserProfileDTO>> getFriendships(){

		var userId = tokenService.getSubjectCurrentToken();
		var userProfile = userService.getUserProfile(userId);

		List<UserProfileDTO> friendships = userService.getListFriendships(userProfile.id());

		return ResponseEntity.ok(friendships);

	}

	//Buscar usuário
	@GetMapping("/{id}")
	public ResponseEntity<UserProfileDTO> searchUserId(@PathVariable Long id){

		//var userId = tokenService.getSubjectCurrentToken();
		//var userProfile = userService.getUserProfile(userId);

		var idUserRequest = userService.searchUserId(id);

		return ResponseEntity.ok(idUserRequest);
	}

	//Lista de usuarios(Todos usuarios)
	@GetMapping("/users")
	public ResponseEntity<List<UserProfileDTO>> getUsers(){

		//var userId = tokenService.getSubjectCurrentToken();
		//var userProfile = userService.getUserProfile(userId);

		List<UserProfileDTO> Users = userService.getUsers();

		return ResponseEntity.ok(Users);

	}
}
