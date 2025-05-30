package com.example.social_api.repository;

import com.example.social_api.model.Comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByPost_IdAndDeletedFalse(Long postId, Pageable pageable);
    /*
    automatically implemented by jpa repository class
     */


}
