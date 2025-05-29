package com.example.social_api.controller;

import com.example.social_api.model.Comment;
import com.example.social_api.model.Post;
import com.example.social_api.model.User;
import com.example.social_api.service.CommentService;
import com.example.social_api.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;
    private final PostService postService;

    public CommentController(CommentService commentService, PostService postService) {
        this.commentService = commentService;
        this.postService = postService;
    }

    // GET comments by postId (only non-deleted)
    @GetMapping("/post/{postId}")
    public Page<Comment> getCommentsByPost(@PathVariable Long postId, Pageable pageable) {
        return commentService.getCommentsByPostId(postId, pageable);
    }

    // GET comment by ID
    @GetMapping("{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id) {
        Optional<Comment> commentOpt = commentService.getCommentById(id);
        return commentOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE comment (user inferred from authentication)
    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) {
        // Get authenticated user
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (comment.getPost() == null || comment.getPost().getId() == null) {
            return ResponseEntity.badRequest().build();
        }
        Post post = postService.getPostById(comment.getPost().getId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        comment.setPost(post);
        comment.setAuthorUser(currentUser);

        Comment createdComment = commentService.createCommentWithUser(comment, currentUser);
        return ResponseEntity.ok(createdComment);
    }

    // UPDATE comment
    @PutMapping("/{id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody Comment updatedComment) {
        Optional<Comment> updated = commentService.updateComment(id, updatedComment);
        return updated.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // SOFT DELETE comment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        try {
            commentService.deleteComment(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
