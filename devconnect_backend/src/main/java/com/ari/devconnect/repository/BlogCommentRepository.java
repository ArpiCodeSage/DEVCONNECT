package com.ari.devconnect.repository;

import com.ari.devconnect.model.BlogComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogCommentRepository
        extends JpaRepository<BlogComment, Long> {

    List<BlogComment> findByBlogIdOrderByCreatedAtDesc(
            Long blogId
    );
}
