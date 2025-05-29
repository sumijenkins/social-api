package com.example.social_api.service.imp;

import com.example.social_api.model.Comment;
import com.example.social_api.model.User;
import com.example.social_api.repository.CommentRepository;
import com.example.social_api.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CommentServiceImp implements CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImp(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Page<Comment> getCommentsByPostId(Long postId, Pageable pageable) {
        return commentRepository.findByPost_IdAndDeletedFalse(postId, pageable);
    }

    @Override
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void deleteComment(Long id) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        boolean isAuthor = comment.getAuthorUser().getId().equals(currentUser.getId());
        boolean isPostOwner = comment.getPost().getUser().getId().equals(currentUser.getId());

        if (!isAuthor && !isPostOwner) {
            throw new AccessDeniedException("You are not allowed to delete this comment.");
        }

        comment.setDeleted(true);
        commentRepository.save(comment);
    }

    public Comment createCommentWithUser(Comment comment, User user) {
        comment.setAuthorUser(user);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setDeleted(false);
        return commentRepository.save(comment);
    }


    @Override
    public Optional<Comment> getCommentById(Long id){
        return commentRepository.findById(id);
    }

    @Override

    public Optional<Comment> updateComment(Long commentId, Comment updatedComment) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!comment.getAuthorUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("You are not allowed to modify this comment.");
        }

        comment.setText(updatedComment.getText());
        comment.setUpdatedAt(LocalDateTime.now());
        Comment saved = commentRepository.save(comment);
        return Optional.of(saved);
    }

}
