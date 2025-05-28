package com.example.social_api.service.imp;

import com.example.social_api.model.Comment;
import com.example.social_api.repository.CommentRepository;
import com.example.social_api.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        Comment comment = commentRepository.findById(id).orElseThrow(()-> new RuntimeException("comment not found"));
        comment.setDeleted(true);
        commentRepository.save(comment);  //soft delete so it can be undone
    }

    @Override
    public Optional<Comment> getCommentById(Long id){
        return commentRepository.findById(id);
    }

    @Override
    public Optional<Comment> updateComment(Long commentId, Comment updatedComment) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if(comment.isPresent()){
            Comment existing = comment.get();
            existing.setText(updatedComment.getText());
            existing.setUpdatedAt(LocalDateTime.now());
            Comment saved = commentRepository.save(existing);
            return Optional.of(saved);
        }
        else {
            return Optional.empty();
        }
    }
}
