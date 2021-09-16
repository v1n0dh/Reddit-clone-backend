package org.ragnarok.redditclone.repository;

import org.ragnarok.redditclone.model.Post;
import org.ragnarok.redditclone.model.Subreddit;
import org.ragnarok.redditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllBySubreddit(Subreddit subreddit);

    List<Post> findAllByUser(User user);
}
