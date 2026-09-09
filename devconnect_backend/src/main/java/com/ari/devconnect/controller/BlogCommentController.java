package com.ari.devconnect.controller;

import com.ari.devconnect.dto.BlogCommentRequest;
import com.ari.devconnect.dto.BlogCommentResponse;
import com.ari.devconnect.service.BlogCommentService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blogs")
public class BlogCommentController {

    private final BlogCommentService blogCommentService;

    public BlogCommentController(
            BlogCommentService blogCommentService
    ) {
        this.blogCommentService = blogCommentService;
    }

    @PostMapping("/{blogId}/comments")
    public ResponseEntity<BlogCommentResponse> addComment(
            @PathVariable Long blogId,
            @RequestBody BlogCommentRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {

        String username = userDetails.getUsername();

        BlogCommentResponse response =
                blogCommentService.addComment(
                        blogId,
                        username,
                        request
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{blogId}/comments")
    public ResponseEntity<List<BlogCommentResponse>> getComments(
            @PathVariable Long blogId
    ) {

        List<BlogCommentResponse> comments =
                blogCommentService.getComments(blogId);

        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {

        String username = userDetails.getUsername();

        blogCommentService.deleteComment(
                commentId,
                username
        );

        return ResponseEntity.ok(
                "Comment deleted successfully"
        );
    }
}