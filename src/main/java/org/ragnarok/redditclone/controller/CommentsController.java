package org.ragnarok.redditclone.controller;

import lombok.AllArgsConstructor;
import org.ragnarok.redditclone.dto.CommentsDto;
import org.ragnarok.redditclone.service.CommentsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@AllArgsConstructor
public class CommentsController {

    private final CommentsService commentsService;

    @PostMapping
    public ResponseEntity<?> createComment(@RequestBody CommentsDto commentsDto) {
        commentsService.save(commentsDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<List<CommentsDto>> getAllCommentsByUsername(@PathVariable String username) {
        return ResponseEntity.ok(commentsService.getAllCommentsByUsername(username));
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<List<CommentsDto>> getAllCommentForPost(@PathVariable Long postId) {
        return ResponseEntity.ok(commentsService.getAllCommentsForPost(postId));
    }
}
