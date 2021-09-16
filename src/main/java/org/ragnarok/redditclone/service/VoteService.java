package org.ragnarok.redditclone.service;

import lombok.AllArgsConstructor;
import org.ragnarok.redditclone.dto.VoteDto;
import org.ragnarok.redditclone.exception.ApiException;
import org.ragnarok.redditclone.mapper.VoteMapper;
import org.ragnarok.redditclone.model.Post;
import org.ragnarok.redditclone.model.User;
import org.ragnarok.redditclone.model.Vote;
import org.ragnarok.redditclone.model.VoteType;
import org.ragnarok.redditclone.repository.PostRepository;
import org.ragnarok.redditclone.repository.VoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final VoteMapper voteMapper;
    private final PostRepository postRepository;
    private final AuthService authService;

    @Transactional
    public void vote(VoteDto voteDto) {
        Post post = postRepository.findById(voteDto.getPostId())
                .orElseThrow(() -> new ApiException("Post not found with id - " + voteDto.getPostId()));
        User user = authService.getCurrentUser();
        Optional<Vote> voteByPostAndUser = voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post, user);
        if (voteByPostAndUser.isPresent() && voteByPostAndUser.get().getVoteType().equals(voteDto.getVoteType()))
            throw new ApiException("You have already " + voteDto.getVoteType() + "'d for this post");
        if (VoteType.UPVOTE.equals(voteDto.getVoteType()))
            post.setVoteCount(post.getVoteCount()+1);
        else
            post.setVoteCount(post.getVoteCount()-1);

        voteRepository.save(voteMapper.map(voteDto, post, user));
        postRepository.save(post);
    }
}
