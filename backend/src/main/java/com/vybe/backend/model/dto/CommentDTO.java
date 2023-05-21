package com.vybe.backend.model.dto;


import com.vybe.backend.model.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
        if (comment.getDate() == null)
            this.date = null;
        else
            this.date = comment.getDate().toString();
        this.customerUsername = comment.getCommentedBy().getUsername();
        this.venueId = comment.getVenue().getId();
        this.id = comment.getId();
    }

    public static Set<CommentDTO> toCommentDTOSet(Set<Comment> comments) {
        Set<CommentDTO> commentDTOS = new java.util.HashSet<>(Set.of());
        for (Comment comment : comments) {
            commentDTOS.add(new CommentDTO(comment));
        }
        return commentDTOS;
    }
}
