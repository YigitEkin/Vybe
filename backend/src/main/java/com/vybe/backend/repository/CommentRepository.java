package com.vybe.backend.repository;

import com.vybe.backend.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findAllByVenueId(int venueId);

    List<Comment> findAllByCommentedBy_Username(String customerUsername);

    Integer deleteAllByVenueId(int venueId);

    Integer deleteAllByCommentedBy_Username(String customerUsername);
}
