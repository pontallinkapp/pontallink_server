package com.pontallink_server.pontallink.controllers;

import com.pontallink_server.pontallink.dtos.FriendshipRequestInformationDTO;
import com.pontallink_server.pontallink.dtos.FriendshipRequestDTO;
import com.pontallink_server.pontallink.dtos.FriendshipsIdDTO;
import com.pontallink_server.pontallink.entities.FriendshipsRequest;
import com.pontallink_server.pontallink.services.FriendshipsService;
import com.pontallink_server.pontallink.infra.security.TokenService;
import com.pontallink_server.pontallink.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    @PostMapping("/requests/{idRequest}")
    public ResponseEntity<FriendshipRequestInformationDTO> sendFriendshipRequest(@PathVariable Long idRequest) {

        var userName = tokenService.getSubjectCurrentToken();
        var userProfile = userService.getUserProfile(userName);

        var requestInformation = friendshipsService.sendFriendshipRequest(userProfile.id(), idRequest);

        return ResponseEntity.ok(requestInformation);
    }

    //Lista de Solicitações de Remetentes(quem enviou solicitação) Pendentes
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

    @DeleteMapping("/delete/{idFriendship}")
    public ResponseEntity<String> deleteFriendship(@PathVariable Long idFriendship){

        var userName = tokenService.getSubjectCurrentToken();
        var userProfile = userService.getUserProfile(userName);

        friendshipsService.deleteFriendshipRequest(idFriendship, userProfile.id());

        return ResponseEntity.ok("Pedido rejeitado com sucesso!");
    }

    //Verificação se o USER_ID enviou uma solicitação para FRIEND_ID
    @GetMapping("/requests/status/{friendId}")
    public ResponseEntity<Void> getFriendStatus(@PathVariable Long friendId) {
        // Pega o nome do usuário do token atual
        var userName = tokenService.getSubjectCurrentToken();
        var userProfile = userService.getUserProfile(userName);

        // Verifica se há uma solicitação de amizade pendente
        Optional<FriendshipsRequest> optionalRequest = friendshipsService.friendStatus(userProfile.id(), friendId);

        // Se a solicitação já foi enviada, retorna 200 OK
        if (optionalRequest.isPresent()) {
            return ResponseEntity.ok().build();  // Status 200 OK
        } else {
            // Se não houver solicitação, retorna 404 NOT FOUND
            return ResponseEntity.notFound().build();  // Status 404 Not Found
        }
    }

}
