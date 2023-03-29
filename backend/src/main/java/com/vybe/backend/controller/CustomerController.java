package com.vybe.backend.controller;

import com.vybe.backend.model.dto.CommentDTO;
import com.vybe.backend.model.dto.CustomerCreationDTO;
import com.vybe.backend.model.dto.CustomerDTO;
import com.vybe.backend.service.CommentService;
import com.vybe.backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/customer")
@CrossOrigin(origins = "*", allowedHeaders = "*")
// TODO: restrict origins
public class CustomerController {
    @Resource
    private UserService userService;

    @Resource
    private CommentService commentService;

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


}
