package com.vybe.backend.service;

import com.vybe.backend.exception.CustomerNotFoundException;
import com.vybe.backend.exception.CustomersAlreadyFriendsException;
import com.vybe.backend.exception.FriendshipNotFoundException;
import com.vybe.backend.model.dto.CustomerDTO;
import com.vybe.backend.model.dto.FriendshipDTO;
import com.vybe.backend.model.entity.Customer;
import com.vybe.backend.model.entity.Friendship;
import com.vybe.backend.repository.CustomerRepository;
import com.vybe.backend.repository.FriendshipRepository;
import com.vybe.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FriendshipService {

    CustomerRepository customerRepository;
    FriendshipRepository friendshipRepository;

    @Autowired
    public FriendshipService(CustomerRepository customerRepository, FriendshipRepository friendshipRepository) {
        this.customerRepository = customerRepository;
        this.friendshipRepository = friendshipRepository;
    }

    // method to get friends of a customer
    public List<CustomerDTO> getFriends(String username) {
        if(!customerRepository.existsById(username)) {
            throw new CustomerNotFoundException("Customer with username: " + username + " not found");
        }
        return friendshipRepository.findBySenderAndAcceptedTrueOrReceiverAndAcceptedTrue(customerRepository.findById(username).get()).stream().map(friendship -> {
            if(friendship.getSender().getUsername().equals(username)) {
                return new CustomerDTO(friendship.getReceiver());
            } else {
                return new CustomerDTO(friendship.getSender());
            }
        }).collect(Collectors.toList());
    }

    // method to get outgoing not accepted friend requests of a customer
    public List<CustomerDTO> getOutgoingFriendRequests(String username) {
        if(!customerRepository.existsById(username)) {
            throw new CustomerNotFoundException("Customer with username: " + username + " not found");
        }
        return friendshipRepository.findBySenderAndAcceptedFalse(customerRepository.findById(username).get()).stream().map(friendship -> new CustomerDTO(friendship.getReceiver())).collect(Collectors.toList());
    }

    // method to get incoming not accepted friend requests of a customer
    public List<CustomerDTO> getIncomingFriendRequests(String username) {
        if(!customerRepository.existsById(username)) {
            throw new CustomerNotFoundException("Customer with username: " + username + " not found");
        }
        return friendshipRepository.findByReceiverAndAcceptedFalse(customerRepository.findById(username).get()).stream().map(friendship -> new CustomerDTO(friendship.getSender())).collect(Collectors.toList());
    }

    // method to send a friend request
    public FriendshipDTO sendFriendRequest(String senderUsername, String receiverUsername) {
        if(!customerRepository.existsById(senderUsername)) {
            throw new CustomerNotFoundException("Customer with username: " + senderUsername + " not found");
        }
        if(!customerRepository.existsById(receiverUsername)) {
            throw new CustomerNotFoundException("Customer with username: " + receiverUsername + " not found");
        }
        Customer sender = customerRepository.findById(senderUsername).get();
        Customer receiver = customerRepository.findById(receiverUsername).get();
        if(friendshipRepository.existsBySenderAndReceiver(sender, receiver)) {
            throw new CustomersAlreadyFriendsException("Friend request already sent");
        }
        Friendship friendship = new Friendship(0L, sender, receiver, false);
        return new FriendshipDTO(friendshipRepository.save(friendship));
    }

    // method to accept a friend request
    public FriendshipDTO acceptFriendRequest(String senderUsername, String receiverUsername) {
        if(!customerRepository.existsById(senderUsername)) {
            throw new CustomerNotFoundException("Customer with username: " + senderUsername + " not found");
        }
        if(!customerRepository.existsById(receiverUsername)) {
            throw new CustomerNotFoundException("Customer with username: " + receiverUsername + " not found");
        }
        Customer sender = customerRepository.findById(senderUsername).get();
        Customer receiver = customerRepository.findById(receiverUsername).get();
        if(!friendshipRepository.existsBySenderAndReceiver(sender, receiver)) {
            throw new FriendshipNotFoundException("Friend request not found");
        }
        Friendship friendship = friendshipRepository.findBySenderAndReceiver(sender, receiver);
        friendship.setAccepted(true);
        return new FriendshipDTO(friendshipRepository.save(friendship));
    }

    // method to decline a friend request
    public void declineFriendRequest(String senderUsername, String receiverUsername) {
        if(!customerRepository.existsById(senderUsername)) {
            throw new CustomerNotFoundException("Customer with username: " + senderUsername + " not found");
        }
        if(!customerRepository.existsById(receiverUsername)) {
            throw new CustomerNotFoundException("Customer with username: " + receiverUsername + " not found");
        }
        Customer sender = customerRepository.findById(senderUsername).get();
        Customer receiver = customerRepository.findById(receiverUsername).get();
        if(!friendshipRepository.existsBySenderAndReceiver(sender, receiver)) {
            throw new FriendshipNotFoundException("Friend request not found");
        }
        Friendship friendship = friendshipRepository.findBySenderAndReceiver(sender, receiver);
        friendshipRepository.delete(friendship);
    }

    // method to remove a friend
    public void removeFriend(String senderUsername, String receiverUsername) {
        if(!customerRepository.existsById(senderUsername)) {
            throw new CustomerNotFoundException("Customer with username: " + senderUsername + " not found");
        }
        if(!customerRepository.existsById(receiverUsername)) {
            throw new CustomerNotFoundException("Customer with username: " + receiverUsername + " not found");
        }
        Customer sender = customerRepository.findById(senderUsername).get();
        Customer receiver = customerRepository.findById(receiverUsername).get();
        if(!friendshipRepository.existsBySenderAndReceiverOrReceiverAndSender(sender, receiver)) {
            throw new FriendshipNotFoundException("Friend request not found");
        }

        friendshipRepository.deleteBySenderAndReceiverOrReceiverAndSender(sender, receiver);
    }




}
