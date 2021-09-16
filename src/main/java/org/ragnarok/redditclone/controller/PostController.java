package org.ragnarok.redditclone.controller;

import lombok.AllArgsConstructor;
import org.ragnarok.redditclone.dto.PostRequest;
import org.ragnarok.redditclone.dto.PostResponse;
import org.ragnarok.redditclone.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<?> createPost(@RequestBody PostRequest postRequest) {
        postService.save(postRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id) {
        return ResponseEntity.ok()
                .body(postService.getPost(id));
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPosts() {
        return ResponseEntity.ok()
                .body(postService.getAllPosts());
    }

    @GetMapping("/subreddits/{id}")
    public ResponseEntity<List<PostResponse>> getPostsBySubreddit(@PathVariable Long subredditId) {
        return ResponseEntity.ok()
                .body(postService.getPostsBySubredditId(subredditId));
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<List<PostResponse>> getPostsByUserId(@PathVariable String username) {
        return ResponseEntity.ok()
                .body(postService.getPostsByUserId(username));
    }
}
