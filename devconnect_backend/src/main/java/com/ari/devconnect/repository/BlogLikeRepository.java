package com.ari.devconnect.repository;

import com.ari.devconnect.model.BlogLike;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface BlogLikeRepository
        extends JpaRepository<BlogLike, Long> {

    Optional<BlogLike> findByUserIdAndBlogId(
            Long userId,
            Long blogId
    );

    long countByBlogId(Long blogId);

    @Modifying
    @Transactional
    void deleteByBlogId(Long blogId);

    @Modifying
    @Transactional
    void deleteByUserId(Long userId);
}