package com.vybe.backend.model.dto;


import com.vybe.backend.model.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentDTO {
    private Integer id;
    private String text;
    private String date;
    private String customerUsername;
    private Integer venueId;

    public Comment toComment() {
        Comment comment = new Comment();
        comment.setText(text);
        comment.setDate(date);

        return comment;
    }

    public CommentDTO( String text, String date, String customerUsername, Integer venueId) {
        this.text = text;
        this.date = date;
        this.customerUsername = customerUsername;
        this.venueId = venueId;
    }

    public CommentDTO(Comment comment) {
        this.text = comment.getText();
        this.date = comment.getDate();
        this.customerUsername = comment.getCommentedBy().getUsername();
        this.venueId = comment.getVenue().getId();
    }

    public static Set<CommentDTO> toCommentDTOSet(Set<Comment> comments) {
        Set<CommentDTO> commentDTOS = Set.of();
        for (Comment comment : comments) {
            commentDTOS.add(new CommentDTO(comment));
        }
        return commentDTOS;
    }
}
