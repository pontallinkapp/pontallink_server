package com.pontallink_server.pontallink.services;

import com.pontallink_server.pontallink.dtos.FriendshipRequestInformationDTO;
import com.pontallink_server.pontallink.dtos.FriendshipsIdDTO;
import com.pontallink_server.pontallink.dtos.NotificationDTO;
import com.pontallink_server.pontallink.entities.FriendshipStatus;
import com.pontallink_server.pontallink.entities.FriendshipsRequest;
import com.pontallink_server.pontallink.entities.User;
import com.pontallink_server.pontallink.repositories.FriendshipsRequestRepository;
import com.pontallink_server.pontallink.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FriendshipsService {

    @Autowired
    private UserRepository userRepository;

    private FriendshipsRequest friendshipsRequest;

    @Autowired
    private FriendshipsRequestRepository friendshipsRequestRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    //Método para enviar solicitações de Amizade
    public FriendshipRequestInformationDTO sendFriendshipRequest(Long senderId, Long receiverId) throws EntityNotFoundException {
        User sender = userRepository.findById(senderId).orElseThrow(() -> new EntityNotFoundException("Remetente não encontrado com id: " + senderId));
        User receiver = userRepository.findById(receiverId).orElseThrow(() -> new EntityNotFoundException("Receptor não encontrado com id " + receiverId));

        // Verificar se já existe uma solicitação de amizade entre o remetente e o receptor
        Optional<FriendshipsRequest> existingRequest = friendshipsRequestRepository.findBySenderAndReceiver(sender, receiver);

        if(existingRequest.isPresent()) {
            FriendshipsRequest friendshipsRequest = existingRequest.get();

            if(friendshipsRequest.getStatus().equals(FriendshipStatus.PENDING) || friendshipsRequest.getStatus().equals(FriendshipStatus.ACCEPTED)) {
                throw new RuntimeException("Solicitação já enviada ou já são amigos!");
            } else if(friendshipsRequest.getStatus().equals(FriendshipStatus.DECLINED)){
                // Atualizar a solicitação existente para PENDING, se anteriormente foi DECLINED
                friendshipsRequest.setStatus(FriendshipStatus.PENDING);
                friendshipsRequestRepository.save(friendshipsRequest);
                return new FriendshipRequestInformationDTO(friendshipsRequest);
            }
        }

        //Se não houver uma solicitação existente, criar uma nova
        FriendshipsRequest friendshipRequest = new FriendshipsRequest();
        friendshipRequest.setSender(sender);
        friendshipRequest.setReceiver(receiver);
        friendshipRequest.setCreatedAt(LocalDateTime.now());
        friendshipRequest.setStatus(FriendshipStatus.PENDING);

        FriendshipsRequest savedRequest = friendshipsRequestRepository.save(friendshipRequest);

        // Envia notificação via WebSocket para o usuário receptor
        messagingTemplate.convertAndSend("/topic/friendships/" + receiverId,
                new NotificationDTO("PENDING", senderId));

        return new FriendshipRequestInformationDTO(savedRequest);
    }

    /*// Método para encontrar todas as solicitações de amizade para um usuário específico
    public List<FriendshipsRequest> getFriendshipRequestsForUser(Long receiverId) {
        return friendshipsRequestRepository.findByReceiverId(receiverId);
    }*/

    // Método para obter a lista de senders para um receptor específico
    public List<FriendshipsIdDTO> getSendersForUser(Long receiverId) {
        List<FriendshipsRequest> requests = friendshipsRequestRepository.findPendingRequestsByReceiverId(receiverId);

        // Mapeia as solicitações para o DTO do sender
        return requests.stream().map(request -> new FriendshipsIdDTO(request.getSender(), request)).collect(Collectors.toList());

    }

    //Método para aceitar uma solicitação de amizade
    public FriendshipsRequest acceptFriendshipRequest(Long idFriendshipRequest, Long userId) throws EntityNotFoundException {
        //Busca a solicitação de amizade pelo ID
        Optional<FriendshipsRequest> optionalRequest = friendshipsRequestRepository.findById(idFriendshipRequest);
        if(optionalRequest.isEmpty()){
            throw new RuntimeException("Solicitação de amizade não encontrada!");
        }

        FriendshipsRequest request = optionalRequest.get();

        //Verifica se o usuário atual é o destinatario da solicitação!
        if(!request.getReceiver().getId().equals(userId)){
            throw new RuntimeException("Usuário não autorizado para aceitar esta solicitação!");
        }

        // Atualiza o status da solicitação para "aceita"
        request.setStatus(FriendshipStatus.ACCEPTED);

        // Salva a solicitação atualizada no banco de dados
        FriendshipsRequest savedRequest = friendshipsRequestRepository.save(request);

        // Envia notificação via WebSocket para o usuário que enviou a solicitação (sender)
        Long senderId = request.getSender().getId();
        Long receiverId = request.getReceiver().getId();

        messagingTemplate.convertAndSend("/topic/friendships/" + senderId,
                new NotificationDTO("ACCEPTED", receiverId));

        return savedRequest;
    }

    //Método para recusar uma solicitação de amizade
    public FriendshipsRequest rejectFriendshipRequest(Long idFriendshipRequest, Long userId) throws EntityNotFoundException {
        //Busca a solicitação de amizade pelo ID
        Optional<FriendshipsRequest> optionalRequest = friendshipsRequestRepository.findById(idFriendshipRequest);

        if(optionalRequest.isEmpty()){
            throw new RuntimeException("Solicitação de amizade não encontrada!");
        }

        FriendshipsRequest request = optionalRequest.get();

        //Verifica se o usuário atual é o destinatario da solicitação!
        if(!request.getReceiver().getId().equals(userId)){
            throw new RuntimeException("Usuário não autorizado para rejeitar esta solicitação!");
        }

        //Atualiza o status da solicitação para "recusado"
        request.setStatus(FriendshipStatus.DECLINED);

        // Salva a solicitação atualizada no banco de dados
        FriendshipsRequest savedRequest = friendshipsRequestRepository.save(request);

        // Envia notificação via WebSocket para o usuário que enviou a solicitação (sender)
        Long senderId = request.getSender().getId();
        Long receiverId = request.getReceiver().getId();

        messagingTemplate.convertAndSend("/topic/friendships/" + senderId,
                new NotificationDTO("DECLINED", receiverId));

        return savedRequest;
    }


    //Método para excluir amizade
    public FriendshipsRequest deleteFriendshipRequest(Long idFriendshipRequest, Long userId) throws EntityNotFoundException {
        Optional<FriendshipsRequest> optionalRequest = friendshipsRequestRepository.findById(idFriendshipRequest);

        if(optionalRequest.isEmpty()){
            throw new RuntimeException("Solicitação de amizade não encontrada!");
        }

        FriendshipsRequest request = optionalRequest.get() ;

        //Veridica se o usuário atual tem vinculo com amizade!
        if(!request.getReceiver().getId().equals(userId) && !request.getSender().getId().equals(userId)){
            throw new RuntimeException("Usuário não autorizado para excluir amizade!");
        }

        //Atualiza o status da solicitação para "recusado"
        request.setStatus(FriendshipStatus.DECLINED);

        // Salva a solicitação atualizada no banco de dados
        FriendshipsRequest savedRequest = friendshipsRequestRepository.save(request);

        // Envia notificação via WebSocket para o usuário que enviou a solicitação (sender)
        Long senderId = request.getSender().getId();
        Long receiverId = request.getReceiver().getId();

        messagingTemplate.convertAndSend("/topic/friendships/" + senderId,
                new NotificationDTO("DECLINED", receiverId));

        return savedRequest;
    }

    //Metodo para verificar solicitação de amizade
    public Optional<FriendshipsRequest> friendStatus(Long userId, Long friendId) {
        // Busca uma solicitação de amizade pendente
         return friendshipsRequestRepository.checkStatus(userId, friendId);
    }

    ///Método para atualizar status de solicitação amizade

}
