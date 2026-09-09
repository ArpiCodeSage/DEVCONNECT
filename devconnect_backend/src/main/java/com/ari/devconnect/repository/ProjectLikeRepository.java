package com.ari.devconnect.repository;
import com.ari.devconnect.model.ProjectLike;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ProjectLikeRepository extends JpaRepository<ProjectLike,Long>{
     Optional<ProjectLike> findByUserIdAndProjectId(
            Long userId,
            Long projectId
    );

    long countByProjectId(Long projectId);
    
}
