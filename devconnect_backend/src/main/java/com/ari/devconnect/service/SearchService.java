package com.ari.devconnect.service;

import com.ari.devconnect.dto.SearchResponse;
import com.ari.devconnect.model.Project;
import com.ari.devconnect.model.User;
import com.ari.devconnect.repository.ProjectRepository;
import com.ari.devconnect.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public SearchService(
            ProjectRepository projectRepository,
            UserRepository userRepository
    ) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public SearchResponse search(String query) {

        if (query == null || query.trim().isEmpty()) {
            return new SearchResponse(List.of(), List.of());
        }

        String cleanQuery = query.trim();

        // Search developers by skills
        List<SearchResponse.DeveloperResult> developers =
                userRepository.searchUsersBySkills(cleanQuery)
                        .stream()
                        .filter(user -> user.getProfile() != null)
                        .map(user -> new SearchResponse.DeveloperResult(
                                user.getUsername(),
                                user.getProfile().getHeadline(),
                                user.getProfile().getSkills(),
                                user.getProfile().getAvatarUrl()
                        ))
                        .toList();

        // Search projects by title, description or tech stack
        List<SearchResponse.ProjectResult> projects =
                projectRepository.searchProjects(cleanQuery)
                        .stream()
                        .map(project -> new SearchResponse.ProjectResult(
                                project.getId(),
                                project.getTitle(),
                                project.getDescription(),
                                project.getTechStack(),
                                project.getUser().getUsername()
                        ))
                        .toList();

        return new SearchResponse(developers, projects);
    }
}