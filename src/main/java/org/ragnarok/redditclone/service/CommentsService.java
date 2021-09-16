package org.ragnarok.redditclone.service;

import lombok.AllArgsConstructor;
import org.ragnarok.redditclone.dto.CommentsDto;
import org.ragnarok.redditclone.exception.ApiException;
import org.ragnarok.redditclone.mapper.CommentsMapper;
import org.ragnarok.redditclone.model.Comment;
import org.ragnarok.redditclone.model.NotificationEmail;
import org.ragnarok.redditclone.model.Post;
import org.ragnarok.redditclone.model.User;
import org.ragnarok.redditclone.repository.CommentRepository;
import org.ragnarok.redditclone.repository.PostRepository;
import org.ragnarok.redditclone.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentsService {

    public static String POST_URL = "";

    private final CommentsMapper commentsMapper;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentRepository commentRepository;
    private final MailService mailService;

    @Transactional
    public void save(CommentsDto commentsDto) {
        Post post = postRepository.findById(commentsDto.getPostId())
                .orElseThrow(() -> new ApiException("Post not found with PostId - " + commentsDto.getPostId()));
        Comment comment = commentsMapper.map(commentsDto, post, authService.getCurrentUser());
        commentRepository.save(comment);

        POST_URL = "http://localhost:8080/api/comments/posts/" + comment.getPost().getPostId() + "/";
        String message = post.getUser().getUsername() + " posted a comment on your post.\n" + POST_URL;
        sendCommentNotification(message, post.getUser());
    }

    private void sendCommentNotification(String message, User user) {
        mailService.sendEmail(new NotificationEmail(user.getUsername() + " commented on your post", user.getEmail(), message));
    }

    @Transactional(readOnly = true)
    public List<CommentsDto> getAllCommentsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException("User not found with username - " + username));
        return commentRepository.findByUser(user)
                .stream()
                .map(commentsMapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CommentsDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ApiException("Post not found with PostId - " + postId));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentsMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
