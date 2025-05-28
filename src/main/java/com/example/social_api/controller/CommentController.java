package com.example.social_api.controller;

import com.example.social_api.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.social_api.service.CommentService;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @GetMapping("/post/{postId}")
    public Page<Comment> getCommentsByPost(@PathVariable Long postId, Pageable pageable){
        return commentService.getCommentsByPostId(postId,pageable);
    }

    @GetMapping("{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id){
        Optional<Comment> commentOpt = commentService.getCommentById(id);
        return commentOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment){
        Comment createdComment = commentService.createComment(comment);
        return ResponseEntity.ok(createdComment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody Comment updatedComment){
        Optional<Comment> updated = commentService.updateComment(id, updatedComment);
        return updated.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }

    // Soft delete comment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        try{
            commentService.deleteComment(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

}
