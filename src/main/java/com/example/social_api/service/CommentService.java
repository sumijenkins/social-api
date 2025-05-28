package com.example.social_api.service;
import com.example.social_api.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface CommentService {
    Page<Comment> getCommentsByPostId(Long postId, Pageable pageable);
    Optional<Comment> getCommentById(Long commentId);
    Optional<Comment> updateComment(Long commentId, Comment updatedComment);
    Comment createComment(Comment comment);
    void deleteComment(Long id);
}
