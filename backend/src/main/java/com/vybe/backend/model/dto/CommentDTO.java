package com.vybe.backend.model.dto;


import com.vybe.backend.model.entity.Comment;
import com.vybe.backend.model.entity.Image;

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
    private String customerName;
    private String customerSurname;
    private ImageDTO customerProfilePicture;
    private Integer venueId;

    public Comment toComment() {
        Comment comment = new Comment();
        comment.setText(text);

        return comment;
    }

    public CommentDTO( String text, String date, String customerUsername, String customerName, String customerSurname, Integer venueId) {
        this.text = text;
        this.date = date;
        this.customerUsername = customerUsername;
        this.venueId = venueId;
        this.customerName = customerName;
        this.customerSurname = customerSurname;
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
        this.customerName = comment.getCommentedBy().getName();
        this.customerSurname = comment.getCommentedBy().getSurname();
        this.customerProfilePicture = new ImageDTO(comment.getCommentedBy().getProfilePicture());
    }

    public static Set<CommentDTO> toCommentDTOSet(Set<Comment> comments) {
        Set<CommentDTO> commentDTOS = new java.util.HashSet<>(Set.of());
        for (Comment comment : comments) {
            commentDTOS.add(new CommentDTO(comment));
        }
        return commentDTOS;
    }
}
