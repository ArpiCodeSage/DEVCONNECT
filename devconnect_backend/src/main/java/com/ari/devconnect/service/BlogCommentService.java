package com.ari.devconnect.service;

import com.ari.devconnect.dto.BlogCommentRequest;
import com.ari.devconnect.dto.BlogCommentResponse;
import com.ari.devconnect.model.Blog;
import com.ari.devconnect.model.BlogComment;
import com.ari.devconnect.model.User;
import com.ari.devconnect.repository.BlogCommentRepository;
import com.ari.devconnect.repository.BlogRepository;
import com.ari.devconnect.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogCommentService {

    private final BlogCommentRepository blogCommentRepository;
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;

    public BlogCommentService(
            BlogCommentRepository blogCommentRepository,
            BlogRepository blogRepository,
            UserRepository userRepository
    ) {
        this.blogCommentRepository = blogCommentRepository;
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
    }

    public BlogCommentResponse addComment(
            Long blogId,
            String username,
            BlogCommentRequest request
    ) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found: " + username
                        )
                );

        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Blog not found: " + blogId
                        )
                );

        BlogComment comment = new BlogComment();

        comment.setContent(request.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUser(user);
        comment.setBlog(blog);

        BlogComment savedComment =
                blogCommentRepository.save(comment);

        return mapToResponse(savedComment);
    }

    public List<BlogCommentResponse> getComments(
            Long blogId
    ) {

        if (!blogRepository.existsById(blogId)) {
            throw new RuntimeException(
                    "Blog not found: " + blogId
            );
        }

        List<BlogComment> comments =
                blogCommentRepository
                        .findByBlogIdOrderByCreatedAtDesc(blogId);

        return comments.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void deleteComment(
            Long commentId,
            String username
    ) {

        BlogComment comment =
                blogCommentRepository.findById(commentId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Comment not found: " + commentId
                                )
                        );

        if (!comment.getUser().getUsername().equals(username)) {
            throw new RuntimeException(
                    "You are not authorized to delete this comment!"
            );
        }

        blogCommentRepository.delete(comment);
    }

    private BlogCommentResponse mapToResponse(
            BlogComment comment
    ) {

        return new BlogCommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getUsername(),
                comment.getCreatedAt()
        );
    }
}