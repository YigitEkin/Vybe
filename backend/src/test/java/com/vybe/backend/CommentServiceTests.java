package com.vybe.backend;

import com.vybe.backend.exception.CustomerNotFoundException;
import com.vybe.backend.exception.VenueNotFoundException;
import com.vybe.backend.model.dto.CommentDTO;
import com.vybe.backend.model.entity.Comment;
import com.vybe.backend.model.entity.Customer;
import com.vybe.backend.model.entity.Venue;
import com.vybe.backend.repository.CommentRepository;
import com.vybe.backend.repository.CustomerRepository;
import com.vybe.backend.repository.VenueRepository;
import com.vybe.backend.service.CommentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.assertj.core.api.Assertions.assertThat;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class CommentServiceTests {
    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private VenueRepository venueRepository;

    // ********** ADD COMMENT TESTS **********
    @Test
    void should_add_comment_successfully() {
        // Arrange
        Venue venue = new Venue();
        venue.setId(1);

        Customer customer = new Customer();
        customer.setUsername("username1");

        Comment comment = new Comment();
        comment.setId(1);
        comment.setText("test comment");
        comment.setCommentedBy(customer);
        comment.setVenue(venue);

        CommentDTO commentDTO = new CommentDTO("This is a test comment.", "test_date", "testusername", 1);
        when(venueRepository.existsById(commentDTO.getVenueId())).thenReturn(true);
        when(customerRepository.existsById(commentDTO.getCustomerUsername())).thenReturn(true);
        when(venueRepository.findById(commentDTO.getVenueId())).thenReturn(Optional.of(venue));
        when(customerRepository.findById(commentDTO.getCustomerUsername())).thenReturn(Optional.of(customer));
        when(commentRepository.save(ArgumentMatchers.any(Comment.class))).thenReturn(comment);

        // Act
        CommentDTO actual = commentService.addComment(commentDTO);

        // Assert
        assertThat(actual).isNotNull();
        verify(venueRepository, times(1)).existsById(commentDTO.getVenueId());
        verify(customerRepository, times(1)).existsById(commentDTO.getCustomerUsername());
        verify(venueRepository, times(1)).findById(commentDTO.getVenueId());
        verify(customerRepository, times(1)).findById(commentDTO.getCustomerUsername());
        verify(commentRepository, times(1)).save(ArgumentMatchers.any(Comment.class));
        verifyNoMoreInteractions(venueRepository, customerRepository, commentRepository);
    }

    @Test
    void should_throw_VenueNotFoundException_when_venue_does_not_exist() {
        // Arrange
        CommentDTO commentDTO = new CommentDTO("This is a test comment.", "test_date", "testusername", 1);
        when(venueRepository.existsById(commentDTO.getVenueId())).thenReturn(false);

        // Act & Assert
        Assertions.assertThrows(VenueNotFoundException.class, () -> commentService.addComment(commentDTO));
        verify(venueRepository, times(1)).existsById(commentDTO.getVenueId());
        verifyNoMoreInteractions(venueRepository, customerRepository, commentRepository);
    }

    @Test
    void should_throw_CustomerNotFoundException_when_customer_does_not_exist() {
        // Arrange
        CommentDTO commentDTO = new CommentDTO("This is a test comment.", "test_date", "testusername", 1);
        when(venueRepository.existsById(commentDTO.getVenueId())).thenReturn(true);
        when(customerRepository.existsById(commentDTO.getCustomerUsername())).thenReturn(false);

        // Act & Assert
        Assertions.assertThrows(CustomerNotFoundException.class, () -> commentService.addComment(commentDTO));
        verify(venueRepository, times(1)).existsById(commentDTO.getVenueId());
        verify(customerRepository, times(1)).existsById(commentDTO.getCustomerUsername());
        verifyNoMoreInteractions(venueRepository, customerRepository, commentRepository);
    }

    // ********** GET ALL COMMENTS TESTS **********
    @Test
    void should_return_all_comments() {
        // Arrange
        Venue venue = new Venue();
        venue.setId(1);

        Customer customer = new Customer();
        customer.setUsername("username1");

        Comment comment = new Comment();
        comment.setId(1);
        comment.setText("test comment");
        comment.setCommentedBy(customer);
        comment.setVenue(venue);
        when(commentRepository.findAll()).thenReturn(Arrays.asList(new Comment(11, "test comment 1", new Date(), customer, venue), new Comment(12, "test comment 2", new Date(), customer, venue)));


        // Act
        List<CommentDTO> actual = commentService.getAllComments();

        // Assert
        assertThat(actual).isNotNull();
        assertThat(actual).hasSize(2);
        verify(commentRepository, times(1)).findAll();
        verifyNoMoreInteractions(venueRepository, customerRepository, commentRepository);
    }


    @Test
    public void should_return_all_comments_for_venue() {
        // Arrange
        Venue venue = new Venue();
        venue.setId(1);

        Customer customer = new Customer();
        customer.setUsername("username1");

        Comment comment1 = new Comment(1, "test comment", new Date(), customer, venue);
        Comment comment2 = new Comment(2, "test comment 2", new Date(), customer, venue);

        when(commentRepository.findAllByVenueId(1)).thenReturn(Arrays.asList(comment1, comment2));
        List<Comment> comments = Arrays.asList(comment1, comment2);
        when(venueRepository.existsById(1)).thenReturn(true);
        when(commentRepository.findAllByVenueId(1)).thenReturn(comments);

        // Act
        List<CommentDTO> actual = commentService.getAllCommentsForVenue(1);

        // Assert
        assertThat(actual).hasSize(2);
        assertThat(actual.get(0).getText()).isEqualTo("test comment");
        assertThat(actual.get(1).getText()).isEqualTo("test comment 2");

        verify(venueRepository, times(1)).existsById(1);
        verify(commentRepository, times(1)).findAllByVenueId(1);
        verifyNoMoreInteractions(venueRepository, customerRepository, commentRepository);
    }

    @Test
    public void should_return_all_comments_by_customer() {
        // Arrange
        String customerUsername = "john_doe";

        Venue venue = new Venue();
        venue.setId(1);

        Customer customer = new Customer();
        customer.setUsername(customerUsername);

        Comment comment1 = new Comment(1, "test comment 1", new Date(), customer, venue);
        Comment comment2 = new Comment(2, "test comment 2", new Date(), customer, venue);

        List<Comment> comments = Arrays.asList(comment1, comment2);
        when(customerRepository.existsById(customerUsername)).thenReturn(true);
        when(commentRepository.findAllByCommentedBy_Username(customerUsername)).thenReturn(comments);

        // Act
        List<CommentDTO> actual = commentService.getAllCommentsByCustomer(customerUsername);

        // Assert
        assertThat(actual).hasSize(2);
        assertThat(actual.get(0).getText()).isEqualTo("test comment 1");
        assertThat(actual.get(1).getText()).isEqualTo("test comment 2");
        verify(customerRepository, times(1)).existsById(customerUsername);
        verify(commentRepository, times(1)).findAllByCommentedBy_Username(customerUsername);
    }

    @Test
    public void should_delete_comment() {
        // Arrange
        int commentId = 1;
        when(commentRepository.existsById(commentId)).thenReturn(true);

        // Act
        commentService.deleteComment(commentId);

        // Assert
        verify(commentRepository, times(1)).deleteById(commentId);
    }

    @Test
    public void should_delete_all_comments_for_venue() {
        // given
        int venueId = 1;
        when(venueRepository.existsById(venueId)).thenReturn(true);

        // when
        commentService.deleteAllCommentsForVenue(venueId);

        // then
        verify(commentRepository, times(1)).deleteAllByVenueId(venueId);
    }

    @Test
    public void should_delete_all_comments_by_customer() {
        // given
        String customerUsername = "john_doe";
        when(customerRepository.existsById(customerUsername)).thenReturn(true);

        // when
        commentService.deleteAllCommentsByCustomer(customerUsername);

        // then
        verify(commentRepository, times(1)).deleteAllByCommentedBy_Username(customerUsername);
    }
}
