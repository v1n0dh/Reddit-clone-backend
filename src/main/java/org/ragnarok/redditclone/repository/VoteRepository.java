package org.ragnarok.redditclone.repository;

import org.ragnarok.redditclone.model.Post;
import org.ragnarok.redditclone.model.User;
import org.ragnarok.redditclone.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User user);
}
