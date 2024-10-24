package com.pontallink_server.pontallink.controllers;

import com.pontallink_server.pontallink.dtos.UserProfileCondominiumDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.pontallink_server.pontallink.dtos.UserProfileDTO;
import com.pontallink_server.pontallink.infra.security.TokenService;
import com.pontallink_server.pontallink.services.UserService;

import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private TokenService tokenService;

	//Consulta usuario logado
	@GetMapping("/me")
	public ResponseEntity<List<UserProfileCondominiumDTO>> getUserProfile() {

		var userLogin = tokenService.getSubjectCurrentToken();
		var userProfile = userService.getUserProfile(userLogin);

		// Adicione logs aqui
		System.out.println("User Profile ID: " + userProfile.id());


		List<UserProfileCondominiumDTO> userProfileCondominiumDTOList = userService.getUserProfileId(userProfile.id());

		System.out.println("DTO: " + userProfileCondominiumDTOList);
		return ResponseEntity.ok(userProfileCondominiumDTOList);

	}

	//Pegar lista de amigo do usuario logado
	@GetMapping("/friendships")
	public ResponseEntity<List<UserProfileDTO>> getFriendships(){

		var userLogin = tokenService.getSubjectCurrentToken();
		var userProfile = userService.getUserProfile(userLogin);

		List<UserProfileDTO> friendships = userService.getListFriendships(userProfile.id());

		return ResponseEntity.ok(friendships);

	}

	//Buscar usuárioo
	@GetMapping("/{id}")
	public ResponseEntity<List<UserProfileCondominiumDTO>> searchUserId(@PathVariable Long id){

		List<UserProfileCondominiumDTO> userProfileCondominiumDTOList = userService.getUserProfileId(id);

		System.out.println("User Profile ID: " + userProfileCondominiumDTOList);

		System.out.println("DTO: " + userProfileCondominiumDTOList);
		return ResponseEntity.ok(userProfileCondominiumDTOList);
	}

	//Lista de usuarios(Todos usuarios)
	@GetMapping("/users")
	public ResponseEntity<List<UserProfileDTO>> getUsers(){

		//var userId = tokenService.getSubjectCurrentToken();
		//var userProfile = userService.getUserProfile(userId);

		List<UserProfileDTO> Users = userService.getUsers();

		return ResponseEntity.ok(Users);

	}

	//Rota que o front usa para verificar o token
	@GetMapping("/verify-token")
	public ResponseEntity<Map<String, Object>> verifyToken(@RequestHeader("Authorization") String tokenJWT) {
		Map<String, Object> response = new HashMap<>();
			try{
				// Remove o prefixo 'Bearer ' se estiver presente
				String cleanToken = tokenJWT.startsWith("Bearer ") ? tokenJWT.substring(7) : tokenJWT;
				System.out.println("Token recebido no método verifyToken: " + cleanToken);

				// Verifica o subject (usuário) e data de expiração do token
				String subject = tokenService.getSubject(cleanToken);
				Date expiration = tokenService.getTokenExpiration(cleanToken);

				response.put("status", "valid");
				response.put("user", subject);
				response.put("expiration", expiration);

				return ResponseEntity.ok(response);

			} catch (RuntimeException e) {
				response.put("status", "invalid");
				response.put("error", "Token JWT inválido ou expirado!");
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
			}
	}

	///search todas as informações disponiveis no campo buscar
	


}
