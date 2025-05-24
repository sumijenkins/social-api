package service.imp;

import com.example.social_api.model.Post;
import com.example.social_api.repository.PostRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import service.PostService;
import java.util.List;

import java.util.Optional;


@Service
public class PostServiceImp implements PostService {

    private final PostRepository postRepository;

    public PostServiceImp(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Optional<Post> getPostById(Long postId) {
        return postRepository.findById(postId);
    }

    @Override
    public Page<Post> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new RuntimeException("Post not found."));
        post.setDeleted(true);
        postRepository.save(post);
    }
}
