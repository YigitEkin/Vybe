package com.vybe.backend.controller;

import com.vybe.backend.model.dto.CommentDTO;
import com.vybe.backend.model.dto.CustomerCreationDTO;
import com.vybe.backend.model.dto.CustomerDTO;
import com.vybe.backend.model.dto.FriendshipDTO;
import com.vybe.backend.service.CommentService;
import com.vybe.backend.service.FriendshipService;
import com.vybe.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*", allowedHeaders = "*")
// TODO: restrict origins
public class CustomerController {
    @Resource private UserService userService;

    @Resource private CommentService commentService;

    @Resource private FriendshipService friendshipService;

    // ************* Customer Endpoints ************* //
    // get all customers
    @GetMapping()
    public Iterable<CustomerDTO> getAllCustomers() {
        return userService.getAllCustomers();
    }

    // get specific customer
    @GetMapping("/{username}")
    public CustomerDTO getCustomer(@PathVariable String username) {
        return userService.getCustomer(username);
    }

    // create a customer
    @PostMapping()
    public CustomerDTO createCustomer(@RequestBody CustomerCreationDTO customerCreationDTO) {
        return userService.addCustomer(customerCreationDTO);
    }

    // update a customer
    @PutMapping("/{username}")
    public CustomerDTO updateCustomer(@PathVariable String username, @RequestBody CustomerCreationDTO customerCreationDTO) {
        return userService.updateCustomer(customerCreationDTO);
    }

    // delete a customer
    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteCustomer(@PathVariable String username) {
        userService.deleteCustomer(username);
        return ResponseEntity.ok("Customer: " + username + " Deleted");
    }

    // ************** Comment Endpoints ************** //
    // get all comments for a customer
    @GetMapping("/{username}/comment")
    public List<CommentDTO> getAllCommentsForCustomer(@PathVariable String username) {
        return commentService.getAllCommentsByCustomer(username);
    }


    // ************** Friendship Endpoints ************** //
    // TODO: can a customer send a friend request to themselves?
    // get all friends of a customer
    @GetMapping("/{username}/friends")
    public List<CustomerDTO> getAllFriendsForCustomer(@PathVariable String username) {
        return friendshipService.getFriends(username);
    }

    // get all outgoing friend requests of a customer
    @GetMapping("/{username}/friends/outgoing_requests")
    public List<CustomerDTO> getAllOutgoingFriendRequestsForCustomer(@PathVariable String username) {
        return friendshipService.getOutgoingFriendRequests(username);
    }

    // get all incoming friend requests of a customer
    @GetMapping("/{username}/friends/incoming_requests")
    public List<CustomerDTO> getAllIncomingFriendRequestsForCustomer(@PathVariable String username) {
        return friendshipService.getIncomingFriendRequests(username);
    }

    // send a friend request
    @PostMapping("/{username}/friends")
    public FriendshipDTO sendFriendRequest(@PathVariable String username, @RequestBody String friendUsername) {
        return friendshipService.sendFriendRequest(username, friendUsername);
    }

    // accept/reject a friend request
    @PutMapping("/{username}/friends/{friendUsername}")
    public FriendshipDTO acceptFriendRequest(@PathVariable String username, @PathVariable String friendUsername, @RequestBody String status) {
        if (Boolean.parseBoolean(status)) {
            return friendshipService.acceptFriendRequest(username, friendUsername);
        } else {
            friendshipService.declineFriendRequest(username, friendUsername);
            return null;
        }
    }

    // unfriend a customer
    @DeleteMapping("/{username}/friends/{friendUsername}")
    public ResponseEntity<String> unfriendCustomer(@PathVariable String username, @PathVariable String friendUsername) {
        friendshipService.removeFriend(username, friendUsername);
        return ResponseEntity.ok("Customer: " + username + " unfriended Customer: " + friendUsername);
    }
}
