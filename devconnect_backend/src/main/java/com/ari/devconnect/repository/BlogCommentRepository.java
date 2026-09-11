package com.ari.devconnect.repository;

import com.ari.devconnect.model.BlogComment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BlogCommentRepository
        extends JpaRepository<BlogComment, Long> {

    List<BlogComment> findByBlogIdOrderByCreatedAtDesc(
            Long blogId
    );

    @Modifying
    @Transactional
    void deleteByBlogId(Long blogId);

    @Modifying
    @Transactional
    void deleteByUserId(Long userId);
}