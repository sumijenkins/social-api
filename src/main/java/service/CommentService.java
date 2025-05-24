package service;
import com.example.social_api.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CommentService {
    Page<Comment> getCommentsByPostId(Long postId, Pageable pageable);
   // Comment getCommentById(Long commentId);

    Comment createComment(Comment comment);
    void deleteComment(Long id);
}
