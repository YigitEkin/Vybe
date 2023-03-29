package com.vybe.backend.controller;

import com.vybe.backend.model.dto.CommentDTO;
import com.vybe.backend.service.CommentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/api/comment")
@CrossOrigin(origins = "*", allowedHeaders = "*")
// TODO: restrict origins
public class CommentController {
    @Resource
    private CommentService commentService;

    // ************* Comment Endpoints ************* //
    // get all comments
    @GetMapping()
    public Iterable<CommentDTO> getAllComments() {
        return commentService.getAllComments();
    }

    // get specific comment
    @GetMapping("/{id}")
    public CommentDTO getComment(@PathVariable Integer id) {
        return commentService.getComment(id);
    }

    // create a comment
    @PostMapping()
    public CommentDTO createComment(@RequestBody CommentDTO commentDTO) {
        return commentService.addComment(commentDTO);
    }

    // update a comment
    @PutMapping("/{id}")
    public CommentDTO updateComment(@PathVariable Integer id, @RequestBody CommentDTO commentDTO) {
        return commentService.updateComment(id, commentDTO);
    }


    // delete a comment
    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Integer id) {
        commentService.deleteComment(id);
    }
}
