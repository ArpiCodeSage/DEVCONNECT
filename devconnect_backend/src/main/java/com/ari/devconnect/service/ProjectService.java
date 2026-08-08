package com.ari.devconnect.service;

import com.ari.devconnect.model.Project;
import com.ari.devconnect.repository.ProjectRepository;
import com.ari.devconnect.dto.ProjectRequest;
import com.ari.devconnect.dto.ProjectResponse;
import com.ari.devconnect.repository.UserRepository;
import com.ari.devconnect.model.User;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public ProjectResponse createProject(String username, ProjectRequest request) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("user not found with username" + username));
         Project project = new Project();
        project.setDescription(request.getDescription());
        project.setTitle(request.getTitle());
        project.setGithubUrl(request.getGithubUrl());
        project.setTechStack(request.getTechStack());
        project.setDemoUrl(request.getDemoUrl());
        project.setUser(user);
        Project savedProject=projectRepository.save(project);
        return mapToResponse(savedProject);


    }
    public List<ProjectResponse> getAllProjects()
    {
        List<Project> projects=projectRepository.findAll();
        return projects.stream().map(this::mapToResponse).collect(Collectors.toList());

    }
    public List<ProjectResponse> getProjectsByUsername(String username)
    {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found: " + username));

        List<Project> projects=projectRepository.findByUserId(user.getId());
        return projects.stream().map(this::mapToResponse).collect(Collectors.toList());
    }
    public void deleteProject(Long projectId,String username)
    {
        Project project=projectRepository.findById(projectId).orElseThrow(()->new RuntimeException("could not find project with id:"+projectId));
        if(!project.getUser().getUsername().equals(username))
        {
            throw new RuntimeException("you are not authorized to delete this project!");
        
        }
                projectRepository.delete(project);



    }
    public ProjectResponse mapToResponse(Project project)
    {
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
//difference between the saving method of this and the one in ProfileService.java:
// That is PERFECT architecture logic!

// In ProfileService: The user and profile already exist in PostgreSQL (with an existing ID), so we just update fields, save, and fetch getProfileByUsername().
// In ProjectService: The project is brand-new, so PostgreSQL has NOT assigned an id yet. Saving it into savedProject captures the newly assigned PostgreSQL id, which mapToResponse then packs into the DTO to send over the web to React!

