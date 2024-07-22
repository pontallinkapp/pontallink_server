package com.pontallink_server.pontallink.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pontallink_server.pontallink.dtos.UserProfileDTO;
import com.pontallink_server.pontallink.infra.security.TokenService;
import com.pontallink_server.pontallink.services.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private TokenService tokenService;

	@GetMapping("/me")
	public ResponseEntity<UserProfileDTO> getUserProfile() {

		var currentSubject = tokenService.getSubjectCurrentToken();
		var userProfile = userService.getUserProfile(currentSubject);
		return ResponseEntity.ok(userProfile);

	}
}
