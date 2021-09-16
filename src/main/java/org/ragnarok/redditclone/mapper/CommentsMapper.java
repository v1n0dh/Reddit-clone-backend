package org.ragnarok.redditclone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.ragnarok.redditclone.dto.CommentsDto;
import org.ragnarok.redditclone.model.Comment;
import org.ragnarok.redditclone.model.Post;
import org.ragnarok.redditclone.model.User;

@Mapper(componentModel = "spring")
public interface CommentsMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "text", source = "commentsDto.text")
    @Mapping(target = "post", source = "post")
    Comment map(CommentsDto commentsDto, Post post, User user);

    @Mapping(target = "postId", expression = "java(comment.getPost().getPostId())")
    @Mapping(target = "username", expression = "java(comment.getUser().getUsername())")
    CommentsDto mapToDto(Comment comment);
}
