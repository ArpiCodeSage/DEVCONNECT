package com.ari.devconnect.service;

import com.ari.devconnect.dto.BlogLikeResponse;
import com.ari.devconnect.model.Blog;
import com.ari.devconnect.model.BlogLike;
import com.ari.devconnect.model.User;
import com.ari.devconnect.repository.BlogLikeRepository;
import com.ari.devconnect.repository.BlogRepository;
import com.ari.devconnect.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BlogLikeService {

    private final BlogLikeRepository blogLikeRepository;
    private final BlogRepository blogRepository;
    private final UserRepository userRepository;

    public BlogLikeService(
            BlogLikeRepository blogLikeRepository,
            BlogRepository blogRepository,
            UserRepository userRepository
    ) {
        this.blogLikeRepository = blogLikeRepository;
        this.blogRepository = blogRepository;
        this.userRepository = userRepository;
    }

    public BlogLikeResponse toggleLike(
            Long blogId,
            String username
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

        Optional<BlogLike> existingLike =
                blogLikeRepository.findByUserIdAndBlogId(
                        user.getId(),
                        blogId
                );

        if (existingLike.isPresent()) {

            blogLikeRepository.delete(existingLike.get());

        } else {

            BlogLike like = new BlogLike();

            like.setUser(user);
            like.setBlog(blog);

            blogLikeRepository.save(like);
        }

        long likeCount =
                blogLikeRepository.countByBlogId(blogId);

        boolean likedByCurrentUser =
                blogLikeRepository
                        .findByUserIdAndBlogId(
                                user.getId(),
                                blogId
                        )
                        .isPresent();

        return new BlogLikeResponse(
                likeCount,
                likedByCurrentUser
        );
    }
}