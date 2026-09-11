package com.ari.devconnect.repository;

import com.ari.devconnect.model.ProjectComment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProjectCommentRepository
        extends JpaRepository<ProjectComment, Long> {

    List<ProjectComment> findByProjectIdOrderByCreatedAtDesc(
            Long projectId
    );

    @Modifying
    @Transactional
    void deleteByProjectId(Long projectId);

    @Modifying
    @Transactional
    void deleteByUserId(Long userId);
}