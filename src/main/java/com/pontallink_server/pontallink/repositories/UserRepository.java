package com.pontallink_server.pontallink.repositories;

import com.pontallink_server.pontallink.dtos.UserProfileCondominiumDTO;
import com.pontallink_server.pontallink.dtos.UserProfileDTO;
import com.pontallink_server.pontallink.entities.FriendshipsRequest;
import com.pontallink_server.pontallink.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByLogin(String login);

    //Buscar usuario pelo id
    @Query("SELECT users.id, users.name, users.bio, condominiums.name, users.userProfileImageMid " +
            "FROM User users " +
            "JOIN Condominium condominiums ON users.condominium.id = condominiums.id " +
            "WHERE users.id = :id")
    List<Object[]> findUserProfileById(Long id);

    //Interesses do usuario
    @Query("SELECT interest.interest " +
            "FROM Interest interest " +
            "JOIN User users ON users.id = interest.user.id " +
            "WHERE users.id = :userId")
    List<String> findInterestsByUserId(Long userId);

    //Buscar amigos
    @Query("SELECT friend.id, friend.sender, friend.receiver, friend.status " +
            "FROM FriendshipsRequest friend " +
            "WHERE (friend.sender.id = :userId OR friend.receiver.id = :userId) " +
            "AND friend.status = 'ACCEPTED'")
    List<Number> findFriendsOnlineById(@Param("userId") Long userId);

    //Buscar numero de amigos
    @Query("SELECT COUNT(friend.id) " +
            "FROM FriendshipsRequest friend " +
            "WHERE (friend.sender.id = :userId OR friend.receiver.id = :userId) " +
            "AND friend.status = 'ACCEPTED'")
    Long countTotalFriends(@Param("userId") Long userId);


    // Método para buscar amigos com base no ID do usuário
    //JPQL
    //Selecione um objeto usuario da tabela USUARIO, user JOIN FriendshipsRequest(aqui estou unindo as tabelas), e no resto do codigo(friendshipsRequest ON (friendshipsRequest.sender.id = :userId OR friendshipsRequest.receiver.id = :userId) AND friendshipsRequest.status = 'ACCEPTED' WHERE user.id != :userId")) é onde verificamos se o ID do usuario atual está em alguma das solicitações, verificando se ele é o enviador ou receptor da solicitação e verifica também se o status dessa solicitação é "ACEITO", além de usarmos um WHERE para remover o ID do usuario atual das opções de amigo.
    //SELECT user FROM User user JOIN = Isso indica que estamos selecionando objetos da entidade;
    //JOIN FriendshipsRequest friendshipsRequest ON = Estamos realizando uma junção entre a tabela User e a tabela FriendshipsRequest. A junção é feita com base na condição especificada dentro do ON.
    @Query("SELECT user FROM User user " +
            "JOIN FriendshipsRequest friendshipsRequest ON (friendshipsRequest.sender.id = user.id OR friendshipsRequest.receiver.id = user.id) " +
            "WHERE (friendshipsRequest.sender.id = :userId OR friendshipsRequest.receiver.id = :userId) " +
            "AND friendshipsRequest.status = 'ACCEPTED' " +
            "AND user.id != :userId")
    List<User> findFriendsByUserId(@Param("userId") Long userId);

}
