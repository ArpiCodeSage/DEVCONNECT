package com.ari.devconnect.service;

import com.ari.devconnect.model.Project;
import com.ari.devconnect.repository.ProjectRepository;
import com.ari.devconnect.dto.ProjectRequest;
import com.ari.devconnect.dto.ProjectResponse;
import com.ari.devconnect.repository.UserRepository;
import com.ari.devconnect.repository.ProjectLikeRepository;
import com.ari.devconnect.repository.ProjectCommentRepository;
import com.ari.devconnect.model.User;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectLikeRepository projectLikeRepository;
    private final ProjectCommentRepository projectCommentRepository;

    public ProjectService(
            ProjectRepository projectRepository,
            UserRepository userRepository,
            ProjectLikeRepository projectLikeRepository,
            ProjectCommentRepository projectCommentRepository
    ) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.projectLikeRepository = projectLikeRepository;
        this.projectCommentRepository = projectCommentRepository;
    }

    public ProjectResponse createProject(String username, ProjectRequest request) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("user not found with username " + username));

        Project project = new Project();

        project.setDescription(request.getDescription());
        project.setTitle(request.getTitle());
        project.setGithubUrl(request.getGithubUrl());
        project.setTechStack(request.getTechStack());
        project.setDemoUrl(request.getDemoUrl());
        project.setUser(user);

        Project savedProject = projectRepository.save(project);

        return mapToResponse(savedProject);
    }

    public List<ProjectResponse> getAllProjects() {

        List<Project> projects = projectRepository.findAll();

        return projects.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ProjectResponse> getProjectsByUsername(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found: " + username));

        List<Project> projects =
                projectRepository.findByUserId(user.getId());

        return projects.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public void deleteProject(Long projectId, String username) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Could not find project with id: " + projectId));

        // Check ownership
        if (!project.getUser().getUsername().equals(username)) {
            throw new RuntimeException(
                    "You are not authorized to delete this project!");
        }

        // Delete likes first
        projectLikeRepository.deleteByProjectId(projectId);

        // Delete comments second
        projectCommentRepository.deleteByProjectId(projectId);

        // Delete project last
        projectRepository.delete(project);
    }

    public ProjectResponse mapToResponse(Project project) {

        return new ProjectResponse(
                project.getId(),
                project.getTitle(),
                project.getDescription(),
                project.getTechStack(),
                project.getGithubUrl(),
                project.getDemoUrl(),
                project.getCreatedAt(),
                project.getUser().getUsername()
        );
    }
}