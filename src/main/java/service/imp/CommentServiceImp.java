package service.imp;

import com.example.social_api.model.Comment;
import com.example.social_api.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import service.CommentService;
@Service
public class CommentServiceImp implements CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImp(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Page<Comment> getCommentsByPostId(Long postId, Pageable pageable) {
        return commentRepository.findByPostIdAndDeletedFalse(postId, pageable);
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


}
