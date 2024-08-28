package com.pontallink_server.pontallink.controllers;

import com.pontallink_server.pontallink.dtos.FriendshipRequestInformationDTO;
import com.pontallink_server.pontallink.dtos.FriendshipRequestDTO;
import com.pontallink_server.pontallink.dtos.FriendshipsIdDTO;
import com.pontallink_server.pontallink.services.FriendshipsService;
import com.pontallink_server.pontallink.infra.security.TokenService;
import com.pontallink_server.pontallink.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friendships")
public class FriendshipsController {

    @Autowired
    private UserService userService;

    @Autowired
    private FriendshipsService friendshipsService;

    @Autowired
    private TokenService tokenService;

    //Enviar solicitação
    @PostMapping("/requests")
    public ResponseEntity<FriendshipRequestInformationDTO> sendFriendshipRequest(@RequestBody FriendshipRequestDTO requestDTO) {

        var userName = tokenService.getSubjectCurrentToken();
        var userProfile = userService.getUserProfile(userName);

        var RequestInformation = friendshipsService.sendFriendshipRequest(userProfile.id(), requestDTO.idUserReceived());
        return ResponseEntity.ok(RequestInformation);
    }

    //Lista de Solicitações de Remetentes Pendentes
    @GetMapping("/requests")
    public ResponseEntity<List<FriendshipsIdDTO>> getSenders() {

        var userName = tokenService.getSubjectCurrentToken();
        var userProfile = userService.getUserProfile(userName);

        List<FriendshipsIdDTO> senders = friendshipsService.getSendersForUser(userProfile.id());
        return ResponseEntity.ok(senders);
    }

    //friendships/requests/:idFriendshipRequest/accept ACEITAR SOLICITAÇÃO
    @PutMapping("/requests/{idFriendshipRequest}/accept")
    public ResponseEntity<String> acceptFriendshipRequest(@PathVariable Long idFriendshipRequest) {

        var userName = tokenService.getSubjectCurrentToken();
        var userProfile = userService.getUserProfile(userName);

        friendshipsService.acceptFriendshipRequest(idFriendshipRequest, userProfile.id());

        return ResponseEntity.ok("Pedido aceito com sucesso!");
    }

    //friendships/requests/:idFriendshipRequest/reject REJEITAR SOLICITAÇÃO
    @PutMapping("/requests/{idFriendshipRequest}/reject")
    public ResponseEntity<String> rejectFriendshipRequest(@PathVariable Long idFriendshipRequest) {

        var userName = tokenService.getSubjectCurrentToken();
        var userProfile = userService.getUserProfile(userName);

        friendshipsService.rejectFriendshipRequest(idFriendshipRequest, userProfile.id());

        return ResponseEntity.ok("Pedido rejeitado com sucesso!");
    }

    @DeleteMapping("/{idFriendship}")
    public ResponseEntity<String> deleteFriendship(@PathVariable Long idFriendship){

        var userName = tokenService.getSubjectCurrentToken();
        var userProfile = userService.getUserProfile(userName);

        friendshipsService.deleteFriendshipRequest(idFriendship, userProfile.id());

        return ResponseEntity.ok("Pedido rejeitado com sucesso!");
    }
}
