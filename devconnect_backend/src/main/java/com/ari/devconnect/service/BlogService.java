package com.ari.devconnect.service;

import com.ari.devconnect.dto.BlogRequest;
import com.ari.devconnect.dto.BlogResponse;
import com.ari.devconnect.model.Blog;
import com.ari.devconnect.model.User;
import com.ari.devconnect.repository.BlogRepository;
import com.ari.devconnect.repository.BlogLikeRepository;
import com.ari.devconnect.repository.BlogCommentRepository;
import com.ari.devconnect.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogService {

    private final BlogRepository blogRepository;
    private final UserRepository userRepository;
    private final BlogLikeRepository blogLikeRepository;
    private final BlogCommentRepository blogCommentRepository;

    public BlogService(
            BlogRepository blogRepository,
            UserRepository userRepository,
            BlogLikeRepository blogLikeRepository,
            BlogCommentRepository blogCommentRepository
    ) {
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
        this.blogLikeRepository = blogLikeRepository;
        this.blogCommentRepository = blogCommentRepository;
    }

    public BlogResponse createBlog(String username, BlogRequest request) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found: " + username));

        Blog blog = new Blog();

        blog.setTitle(request.getTitle());
        blog.setContent(request.getContent());
        blog.setCreatedAt(LocalDateTime.now());
        blog.setUser(user);

        Blog savedBlog = blogRepository.save(blog);

        return mapToResponse(savedBlog);
    }

    public List<BlogResponse> getAllBlogs() {

        List<Blog> blogs = blogRepository.findAll();

        return blogs.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public BlogResponse getBlogById(Long blogId) {

        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() ->
                        new RuntimeException("Blog not found: " + blogId));

        return mapToResponse(blog);
    }

    public List<BlogResponse> getBlogsByUsername(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found: " + username));

        List<Blog> blogs =
                blogRepository.findByUserId(user.getId());

        return blogs.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public BlogResponse updateBlog(
            Long blogId,
            String username,
            BlogRequest request
    ) {

        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() ->
                        new RuntimeException("Blog not found: " + blogId));

        if (!blog.getUser().getUsername().equals(username)) {
            throw new RuntimeException(
                    "You are not authorized to update this blog!");
        }

        blog.setTitle(request.getTitle());
        blog.setContent(request.getContent());

        Blog updatedBlog = blogRepository.save(blog);

        return mapToResponse(updatedBlog);
    }

    public void deleteBlog(Long blogId, String username) {

        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() ->
                        new RuntimeException("Blog not found: " + blogId));

        // Make sure only the owner can delete it
        if (!blog.getUser().getUsername().equals(username)) {
            throw new RuntimeException(
                    "You are not authorized to delete this blog!");
        }

        // Delete likes first
        blogLikeRepository.deleteByBlogId(blogId);

        // Delete comments second
        blogCommentRepository.deleteByBlogId(blogId);

        // Delete blog last
        blogRepository.delete(blog);
    }

    private BlogResponse mapToResponse(Blog blog) {

        return new BlogResponse(
                blog.getId(),
                blog.getTitle(),
                blog.getContent(),
                blog.getUser().getUsername(),
                blog.getCreatedAt()
        );
    }
}