package com.vybe.backend.controller;

import com.vybe.backend.model.dto.*;
import com.vybe.backend.service.CommentService;
import com.vybe.backend.service.FriendshipService;
import com.vybe.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")

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
    @PutMapping("/{username}/friends/{friendUsername}/{isAccept}")
    public FriendshipDTO acceptFriendRequest(@PathVariable String username, @PathVariable String friendUsername, @PathVariable String isAccept) {
        if (isAccept.equals("true")) {
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

    // ************** Streak Endpoints ************** //

    // get all streaks for a customer
    @GetMapping("/{username}/streaks")
    public List<StreakDTO> getAllStreaksForCustomer(@PathVariable String username) {
        return userService.getAllStreaks(username);
    }

    // update streak for a customer at a venue
    @PostMapping("/{username}/streaks/{venueId}")
    public StreakDTO updateStreak(@PathVariable String username, @PathVariable Integer venueId) {
        return userService.updateStreak(username, venueId);
    }

    // get streak for a customer at a venue
    @GetMapping("/{username}/streaks/{venueId}")
    public StreakDTO getStreak(@PathVariable String username, @PathVariable Integer venueId) {
        return userService.getStreak(username, venueId);
    }
}
