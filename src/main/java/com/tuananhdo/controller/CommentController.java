package com.tuananhdo.controller;

import com.tuananhdo.payload.CommentDTO;
import com.tuananhdo.service.CommentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag(name = "REST APIs for Comment Resource")
public class CommentController {

    private final CommentService commentService;
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @SecurityRequirement(name = "Bear Authentication")
    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentDTO> createComment(@PathVariable("postId") long postId,
                                                    @Valid @RequestBody CommentDTO commentDTO) {
        return new ResponseEntity<>(commentService.createComment(postId, commentDTO), HttpStatus.CREATED);
    }

    @SecurityRequirement(name = "Bear Authentication")
    @PutMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComments(@PathVariable("postId") long postId,
                                                     @PathVariable("commentId") long commentId,
                                                     @Valid @RequestBody CommentDTO commentDTO) {
        return new ResponseEntity<>(commentService.updateComment(postId, commentId, commentDTO), HttpStatus.OK);
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentDTO>> getCommentsByPosts(@PathVariable("postId") long postId) {
        return new ResponseEntity<>(commentService.getCommentByPostId(postId), HttpStatus.OK);
    }

    @GetMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> getCommentsByPosts(@PathVariable("postId") long postId,
                                                         @PathVariable("commentId") long commentId) {
        return new ResponseEntity<>(commentService.getCommentById(postId, commentId), HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bear Authentication")
    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComments(@PathVariable("postId") long postId,
                                                 @PathVariable("commentId") long commentId) {
        commentService.deleteCommentById(postId, commentId);
        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
    }
}
