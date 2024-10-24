package com.pontallink_server.pontallink.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pontallink_server.pontallink.entities.FriendshipsRequest;

import java.util.List;
import java.util.Optional;
import com.pontallink_server.pontallink.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FriendshipsRequestRepository extends JpaRepository<FriendshipsRequest, Long> {
    @Query("SELECT friendshipsRequest FROM FriendshipsRequest friendshipsRequest WHERE friendshipsRequest.receiver.id = :receiverId AND friendshipsRequest.status = 'PENDING' ")
    List<FriendshipsRequest> findPendingRequestsByReceiverId(@Param("receiverId") Long receiverId);

    Optional<FriendshipsRequest> findBySenderAndReceiver(User sender, User receiver);

    @Query("SELECT friendshipsRequest FROM FriendshipsRequest friendshipsRequest WHERE friendshipsRequest.sender.id = :userId AND friendshipsRequest.receiver.id = :friendId AND friendshipsRequest.status = 'PENDING' ")
    Optional<FriendshipsRequest> checkStatus(@Param("userId")Long userId, @Param("friendId")Long friendId);
}
