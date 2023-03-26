package com.vybe.backend.model.dto;


import com.vybe.backend.model.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CommentDTO {
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

    public CommentDTO(Comment comment) {
        this.text = comment.getText();
        this.date = comment.getDate();
        this.customerUsername = comment.getCommentedBy().getUsername();
        this.venueId = comment.getVenue().getId();
    }
}
