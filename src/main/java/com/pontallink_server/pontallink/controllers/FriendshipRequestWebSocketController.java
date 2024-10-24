package com.pontallink_server.pontallink.controllers;

import com.pontallink_server.pontallink.dtos.FriendshipsRequestDTO;
import com.pontallink_server.pontallink.dtos.FriendshipsRequestStatusDTO;
import com.pontallink_server.pontallink.dtos.NotificationDTO;
import com.pontallink_server.pontallink.services.FriendshipsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class FriendshipRequestWebSocketController {
    /*
    private final SimpMessagingTemplate messagingTemplate;

    // Injeção de dependência do SimpMessagingTemplate para enviar mensagens via WebSocket
    public FriendshipRequestWebSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // Endepoint para notificações de enviar solicitação
    @MessageMapping("/friendships/requests/{idRequest}")
    @SendTo("/topic/friendships/{userId}")
    public NotificationDTO notifyFriendshipSend(NotificationDTO notification) {
        return notification; // Envia notificação de aceitação
    }

    // Endpoint para notificações de aceitação de solicitação
    @MessageMapping("/friendships/requests/{idFriendshipRequest}/accept")
    @SendTo("/topic/friendships/{userId}")
    public NotificationDTO notifyFriendshipAccepted(NotificationDTO notification) {
        return notification; // Envia notificação de aceitação
    }

    // Endpoint para notificações de recusa de solicitação
    @MessageMapping("/friendships/requests/{idFriendshipRequest}/reject")
    @SendTo("/topic/friendships/{userId}")
    public NotificationDTO notifyFriendshipRejected(NotificationDTO notification) {
        return notification; // Envia notificação de recusa
    }

    // Endpoint para notificações de exclusão de amizade
    @MessageMapping("/friendships/delete/{idFriendship}")
    @SendTo("/topic/friendships/{userId}")
    public NotificationDTO notifyFriendshipDeleted(NotificationDTO notification) {
        return notification; // Envia notificação de exclusão
    }

    @MessageMapping("/chat/sendMessage")
    @SendTo("/topic/chat/{receiverId}")
    public MessageDTO sendMessage(MessageDTO message) {
        // lógica para processar a mensagem e enviar ao destinatário
        return message;
    }

     */
}
