package com.ari.devconnect.repository;

import com.ari.devconnect.model.ProjectComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectCommentRepository
        extends JpaRepository<ProjectComment, Long> {

    List<ProjectComment> findByProjectIdOrderByCreatedAtDesc(Long projectId);
}