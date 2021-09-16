package org.ragnarok.redditclone.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.ragnarok.redditclone.dto.SubredditDto;
import org.ragnarok.redditclone.model.Post;
import org.ragnarok.redditclone.model.Subreddit;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SubredditMapper {

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPost()))")
    SubredditDto mapSubredditToDto(Subreddit subreddit);

    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "post", ignore = true)
    Subreddit mapDtoToSubreddit(SubredditDto subreddit);
}
