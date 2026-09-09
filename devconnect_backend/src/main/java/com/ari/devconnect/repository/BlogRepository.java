package com.ari.devconnect.repository;

import com.ari.devconnect.model.Blog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {

    List<Blog> findByUserId(Long userId);
}