package com.example.social_api.repository;

import com.example.social_api.model.Post;
import org.hibernate.query.Page;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post, Long> {

}
