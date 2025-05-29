package com.example.social_api.controller;
import com.example.social_api.model.Post;
import com.example.social_api.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.example.social_api.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post){
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        post.setUser(currentUser);  // postu API key kullanıcısına bağla
        Post createdPost = postService.createPost(post);

        return ResponseEntity.ok(createdPost);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id){
        Optional<Post> postOpt = postService.getPostById(id);
        return postOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public Page<Post> getAllPosts(Pageable pageable) {
        return postService.getAllPosts(pageable);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/user/{userId}")
    public Page<Post> getPostsByUserId(@PathVariable Long userId, Pageable pageable) {
        return postService.getPostsByUserId(userId, pageable);
    }

}
