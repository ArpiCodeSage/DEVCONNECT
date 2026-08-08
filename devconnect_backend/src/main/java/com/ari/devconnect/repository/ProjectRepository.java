package com.ari.devconnect.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ari.devconnect.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByUserId(Long userId);
    
}
// Optional is for queries expected to return 0 or 1 single item.
// List is for queries expected to return 0, 1, or MANY items.