package com.vybe.backend.repository;

import com.vybe.backend.model.entity.Customer;
import com.vybe.backend.model.entity.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Integer> {
    // method for finding not accepted outgoing friend requests
    List<Friendship> findBySenderAndAcceptedFalse(Customer customer);

    // method for finding not accepted incoming friend requests
    List<Friendship> findByReceiverAndAcceptedFalse(Customer customer);

    // method for finding accepted friends of a customer (meaning find by both sender and receiver)
    @Query("SELECT f FROM Friendship f WHERE (f.sender = ?1 OR f.receiver = ?1) AND f.accepted = true")
    List<Friendship> findBySenderAndAcceptedTrueOrReceiverAndAcceptedTrue(Customer customer);

    boolean existsBySenderAndReceiver(Customer sender, Customer receiver);

    Friendship findBySenderAndReceiver(Customer sender, Customer receiver);

    // detete friendship by sender and receiver or vice versa
    @Modifying
    @Query("DELETE FROM Friendship f WHERE (f.sender = ?1 AND f.receiver = ?2) OR (f.sender = ?2 AND f.receiver = ?1)")
    void deleteBySenderAndReceiverOrReceiverAndSender(Customer sender, Customer receiver);

    @Query("SELECT count(f) > 0 FROM Friendship f WHERE (f.sender = ?1 AND f.receiver = ?2) OR (f.sender = ?2 AND f.receiver = ?1)")
    Boolean existsBySenderAndReceiverOrReceiverAndSender(Customer sender, Customer receiver);
}
