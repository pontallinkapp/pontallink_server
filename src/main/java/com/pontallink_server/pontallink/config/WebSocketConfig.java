package com.pontallink_server.pontallink.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        ///Definir o endpoint WebSocket que o frontend usará para se conectar
        registry.addEndpoint("/ws")
                .setAllowedOrigins("*") //Permite qualquer origem, pode restingir conforme necessario
                .withSockJS(); //Habilita fallback com sockJS para navegadores que não suportam WebSocket
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //Definir prefixos de destino para mensagens enviadas e recebidas
        // Ativa um broker de mensagens em memória
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/topic", "/queue"); //Define o broker de destino para mensagens
    }
}
