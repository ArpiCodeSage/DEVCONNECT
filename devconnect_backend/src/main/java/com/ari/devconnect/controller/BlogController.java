package com.ari.devconnect.controller;

import com.ari.devconnect.dto.BlogRequest;
import com.ari.devconnect.dto.BlogResponse;
import com.ari.devconnect.service.BlogService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    @PostMapping
    public ResponseEntity<BlogResponse> createBlog(
            @RequestBody BlogRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {

        String username = userDetails.getUsername();

        BlogResponse response =
                blogService.createBlog(username, request);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<BlogResponse>> getAllBlogs() {

        List<BlogResponse> blogs =
                blogService.getAllBlogs();

        return ResponseEntity.ok(blogs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogResponse> getBlogById(
            @PathVariable Long id
    ) {

        BlogResponse blog =
                blogService.getBlogById(id);

        return ResponseEntity.ok(blog);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<BlogResponse>> getBlogsByUsername(
            @PathVariable String username
    ) {

        List<BlogResponse> blogs =
                blogService.getBlogsByUsername(username);

        return ResponseEntity.ok(blogs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BlogResponse> updateBlog(
            @PathVariable Long id,
            @RequestBody BlogRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {

        String username = userDetails.getUsername();

        BlogResponse updatedBlog =
                blogService.updateBlog(
                        id,
                        username,
                        request
                );

        return ResponseEntity.ok(updatedBlog);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBlog(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails
    ) {

        String username = userDetails.getUsername();

        blogService.deleteBlog(id, username);

        return ResponseEntity.ok(
                "Blog deleted successfully"
        );
    }
}