package com.ari.devconnect.controller;

import com.ari.devconnect.dto.BlogLikeResponse;
import com.ari.devconnect.service.BlogLikeService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/blogs")
public class BlogLikeController {

    private final BlogLikeService blogLikeService;

    public BlogLikeController(
            BlogLikeService blogLikeService
    ) {
        this.blogLikeService = blogLikeService;
    }

    @PostMapping("/{blogId}/like")
    public ResponseEntity<BlogLikeResponse> toggleLike(
            @PathVariable Long blogId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {

        String username = userDetails.getUsername();

        BlogLikeResponse response =
                blogLikeService.toggleLike(
                        blogId,
                        username
                );

        return ResponseEntity.ok(response);
    }
    @GetMapping("/{blogId}/like")
public ResponseEntity<BlogLikeResponse> getLikeStatus(
        @PathVariable Long blogId,
        @AuthenticationPrincipal UserDetails userDetails
) {

    String username = userDetails.getUsername();

    return ResponseEntity.ok(
            blogLikeService.getLikeStatus(blogId, username)
    );
}
}