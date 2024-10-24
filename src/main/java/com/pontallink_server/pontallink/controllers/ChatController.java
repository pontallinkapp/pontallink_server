package com.pontallink_server.pontallink.controllers;

import com.pontallink_server.pontallink.entities.ChatMessage;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @MessageMapping("/sendMessage")
    @SendTo("/topic/chat")
    public ChatMessage sendeMessage(ChatMessage message, SimpMessageHeaderAccessor headerAcessor){

        //Aqui podemos acessar o ID da sessão WebSocket
        String sessionId = headerAcessor.getSessionId();
        System.out.println("ID da sessão WebSocket: " + sessionId);

        //chatService.saveMessage(message);

        //Envia a mensagem de volta para todos os clientes inscritos em "/topic/chat"
        return message;
    }
}
