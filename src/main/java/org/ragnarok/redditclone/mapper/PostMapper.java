package org.ragnarok.redditclone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.ragnarok.redditclone.dto.PostRequest;
import org.ragnarok.redditclone.dto.PostResponse;
import org.ragnarok.redditclone.model.Post;
import org.ragnarok.redditclone.model.Subreddit;
import org.ragnarok.redditclone.model.User;
import org.ragnarok.redditclone.repository.CommentRepository;
import org.ragnarok.redditclone.repository.VoteRepository;
import org.ragnarok.redditclone.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class PostMapper {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private AuthService authService;

    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "subreddit", source = "subreddit")
    @Mapping(target = "voteCount", constant = "0")
    @Mapping(target = "description", source = "postRequest.description")
    public abstract Post map(PostRequest postRequest, Subreddit subreddit, User user);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "subredditName", source = "subreddit.name")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    public abstract PostResponse mapToDto(Post post);

    Integer commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }
}
