package com.vybe.backend.service;

import com.vybe.backend.exception.CustomerNotFoundException;
import com.vybe.backend.exception.VenueNotFoundException;
import com.vybe.backend.model.dto.CommentDTO;
import com.vybe.backend.model.entity.Comment;
import com.vybe.backend.model.entity.Customer;
import com.vybe.backend.model.entity.Venue;
import com.vybe.backend.repository.CommentRepository;
import com.vybe.backend.repository.CustomerRepository;
import com.vybe.backend.repository.VenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CommentService {

    CommentRepository commentRepository;
    CustomerRepository customerRepository;
    VenueRepository venueRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, CustomerRepository customerRepository, VenueRepository venueRepository) {
        this.commentRepository = commentRepository;
        this.customerRepository = customerRepository;
        this.venueRepository = venueRepository;
    }

    // add a comment
    public CommentDTO addComment(CommentDTO commentCreationDTO) {
        if(!venueRepository.existsById(commentCreationDTO.getVenueId())) {
            throw new VenueNotFoundException("Venue with id: " + commentCreationDTO.getVenueId() + " not found");
        }
        if(!customerRepository.existsById(commentCreationDTO.getCustomerUsername())) {
            throw new CustomerNotFoundException("Customer with username: " + commentCreationDTO.getCustomerUsername() + " not found");
        }
        Venue venue = venueRepository.findById(commentCreationDTO.getVenueId()).get();
        Customer customer = customerRepository.findById(commentCreationDTO.getCustomerUsername()).get();
        Comment comment = commentCreationDTO.toComment();
        comment.setDate(new Date());
        comment.setVenue(venue);
        comment.setCommentedBy(customer);
        return new CommentDTO(commentRepository.save(comment));
    }

    // get all comments
    public List<CommentDTO> getAllComments() {
        return commentRepository.findAll().stream().map(CommentDTO::new).collect(Collectors.toList());
    }

    // get all comments for a venue
    public List<CommentDTO> getAllCommentsForVenue(int venueId) {
        if(!venueRepository.existsById(venueId)) {
            throw new VenueNotFoundException("Venue with id: " + venueId + " not found");
        }
        return commentRepository.findAllByVenueId(venueId).stream().map(CommentDTO::new).collect(Collectors.toList());
    }

    // get all comments by a customer
    public List<CommentDTO> getAllCommentsByCustomer(String customerUsername) {
        if(!customerRepository.existsById(customerUsername)) {
            throw new CustomerNotFoundException("Customer with id: " + customerUsername + " not found");
        }
        return commentRepository.findAllByCommentedBy_Username(customerUsername).stream().map(CommentDTO::new).collect(Collectors.toList());
    }

    // delete a comment
    public void deleteComment(int commentId) {
        if(!commentRepository.existsById(commentId)) {
            throw new CustomerNotFoundException("Comment with id: " + commentId + " not found");
        }
        commentRepository.deleteById(commentId);
    }

    // delete all comments for a venue
    public Integer deleteAllCommentsForVenue(int venueId) {
        if(!venueRepository.existsById(venueId)) {
            throw new VenueNotFoundException("Venue with id: " + venueId + " not found");
        }
        return commentRepository.deleteAllByVenueId(venueId);
    }

    // delete all comments by a customer
    public Integer deleteAllCommentsByCustomer(String customerUsername) {
        if(!customerRepository.existsById(customerUsername)) {
            throw new CustomerNotFoundException("Customer with id: " + customerUsername + " not found");
        }
        return commentRepository.deleteAllByCommentedBy_Username(customerUsername);
    }

    public CommentDTO getComment(Integer id) {
        if(!commentRepository.existsById(id)) {
            throw new CustomerNotFoundException("Comment with id: " + id + " not found");
        }

        return new CommentDTO(commentRepository.findById(id).get());
    }

    // update a comment
    public CommentDTO updateComment(Integer id, CommentDTO commentDTO) {
        if(!commentRepository.existsById(id)) {
            throw new CustomerNotFoundException("Comment with id: " + commentDTO.getId() + " not found");
        }

        Comment originalComment = commentRepository.findById(id).get();
        Comment comment = commentDTO.toComment();
        comment.setVenue(originalComment.getVenue());
        comment.setCommentedBy(originalComment.getCommentedBy());
        comment.setId(originalComment.getId());
        return new CommentDTO(commentRepository.save(comment));
    }

    // TODO: update comment
}
