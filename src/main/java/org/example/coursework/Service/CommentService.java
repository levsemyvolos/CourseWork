package org.example.coursework.Service;

import org.example.coursework.model.Comment;
import org.example.coursework.model.Game;
import org.example.coursework.model.User;
import org.example.coursework.repository.CommentRepository;
import org.example.coursework.repository.GameRepository;
import org.example.coursework.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {

    private static final Logger logger = LoggerFactory.getLogger(CommentService.class);

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    public List<Comment> getAllComments() {
        logger.info("Fetching all comments");
        return commentRepository.findAll();
    }

    public Comment createComment(Long userId, Long gameId, String content) {
        logger.info("Creating comment");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setGame(game);
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());

        logger.info("Comment created: {}", comment);

        return commentRepository.save(comment);
    }

    public Comment getCommentById(Long id) {
        logger.info("Fetching comment with id {}", id);
        return commentRepository.findById(id).orElse(null);
    }

    public Comment updateComment(Long id, Comment updatedComment) {
        logger.info("Updating comment with id {}", id);

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        comment.setContent(updatedComment.getContent());
        comment.setCreatedAt(updatedComment.getCreatedAt());
        comment.setUser(updatedComment.getUser());
        comment.setGame(updatedComment.getGame());

        logger.info("Updated comment: {}", comment);

        return commentRepository.save(comment);
    }

    public void deleteComment(Long id) {
        logger.info("Deleting comment with id {}", id);
        commentRepository.deleteById(id);
        logger.info("Comment deleted successfully");
    }
}


