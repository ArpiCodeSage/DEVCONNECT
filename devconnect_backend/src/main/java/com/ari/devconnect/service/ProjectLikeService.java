package com.ari.devconnect.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.ari.devconnect.dto.ProjectLikeResponse;
import com.ari.devconnect.model.Project;
import com.ari.devconnect.model.ProjectLike;
import com.ari.devconnect.model.User;
import com.ari.devconnect.repository.ProjectLikeRepository;
import com.ari.devconnect.repository.ProjectRepository;
import com.ari.devconnect.repository.UserRepository;

@Service
public class ProjectLikeService {

    
 private final ProjectLikeRepository projectLikeRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectLikeService(
            ProjectLikeRepository projectLikeRepository,
            ProjectRepository projectRepository,
            UserRepository userRepository
    ) {
        this.projectLikeRepository = projectLikeRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public ProjectLikeResponse toggleLike(
            Long projectId,
            String username
    ) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found: " + username)
                );

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Project not found: " + projectId
                        )
                );

        Optional<ProjectLike> existingLike =
                projectLikeRepository.findByUserIdAndProjectId(
                        user.getId(),
                        projectId
                );

        if (existingLike.isPresent()) {

            projectLikeRepository.delete(existingLike.get());

        } else {

            ProjectLike like = new ProjectLike();

            like.setUser(user);
            like.setProject(project);

            projectLikeRepository.save(like);
        }

        long likeCount =
                projectLikeRepository.countByProjectId(projectId);

        boolean likedByCurrentUser =
                projectLikeRepository
                        .findByUserIdAndProjectId(
                                user.getId(),
                                projectId
                        )
                        .isPresent();

        return new ProjectLikeResponse(
                likeCount,
                likedByCurrentUser
        );
    }
}
