package com.ari.devconnect.service;

import com.ari.devconnect.dto.ProjectCommentRequest;
import com.ari.devconnect.dto.ProjectCommentResponse;
import com.ari.devconnect.model.Project;
import com.ari.devconnect.model.ProjectComment;
import com.ari.devconnect.model.User;
import com.ari.devconnect.repository.ProjectCommentRepository;
import com.ari.devconnect.repository.ProjectRepository;
import com.ari.devconnect.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectCommentService {

    private final ProjectCommentRepository projectCommentRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectCommentService(
            ProjectCommentRepository projectCommentRepository,
            ProjectRepository projectRepository,
            UserRepository userRepository
    ) {
        this.projectCommentRepository = projectCommentRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public ProjectCommentResponse addComment(
            Long projectId,
            String username,
            ProjectCommentRequest request
    ) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found: " + username
                        )
                );

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Project not found: " + projectId
                        )
                );

        ProjectComment comment = new ProjectComment();

        comment.setContent(request.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUser(user);
        comment.setProject(project);

        ProjectComment savedComment =
                projectCommentRepository.save(comment);

        return mapToResponse(savedComment);
    }

    public List<ProjectCommentResponse> getComments(Long projectId) {

        if (!projectRepository.existsById(projectId)) {
            throw new RuntimeException(
                    "Project not found: " + projectId
            );
        }

        List<ProjectComment> comments =
                projectCommentRepository
                        .findByProjectIdOrderByCreatedAtDesc(projectId);

        return comments.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void deleteComment(
            Long commentId,
            String username
    ) {

        ProjectComment comment =
                projectCommentRepository.findById(commentId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Comment not found: " + commentId
                                )
                        );

        if (!comment.getUser().getUsername().equals(username)) {
            throw new RuntimeException(
                    "You are not authorized to delete this comment!"
            );
        }

        projectCommentRepository.delete(comment);
    }

    private ProjectCommentResponse mapToResponse(
            ProjectComment comment
    ) {

        return new ProjectCommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getUsername(),
                comment.getCreatedAt()
        );
    }
}