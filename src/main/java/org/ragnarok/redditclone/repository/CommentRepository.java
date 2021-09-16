package org.ragnarok.redditclone.repository;

import org.ragnarok.redditclone.model.Comment;
import org.ragnarok.redditclone.model.Post;
import org.ragnarok.redditclone.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findByUser(User user);
}
