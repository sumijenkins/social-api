package service;

import com.example.social_api.model.Post;
import org.springframework.data.domain.Pageable;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface PostService {
    Post createPost(Post post);
    Optional<Post> getPostById(Long postId);
    Page<Post> getAllPosts(Pageable pageable);
    void deletePost(Long postId);
}
