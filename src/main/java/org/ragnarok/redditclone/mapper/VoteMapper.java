package org.ragnarok.redditclone.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.ragnarok.redditclone.dto.VoteDto;
import org.ragnarok.redditclone.model.Post;
import org.ragnarok.redditclone.model.User;
import org.ragnarok.redditclone.model.Vote;

@Mapper(componentModel = "spring")
public interface VoteMapper {

    @Mapping(target = "post", source = "post")
    @Mapping(target = "user", source = "user")
    Vote map(VoteDto voteDto, Post post, User user);
}
