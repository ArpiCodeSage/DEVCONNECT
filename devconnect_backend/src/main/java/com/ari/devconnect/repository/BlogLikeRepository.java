package com.ari.devconnect.repository;

import com.ari.devconnect.model.BlogLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BlogLikeRepository
        extends JpaRepository<BlogLike, Long> {

    Optional<BlogLike> findByUserIdAndBlogId(
            Long userId,
            Long blogId
    );

    long countByBlogId(Long blogId);
}