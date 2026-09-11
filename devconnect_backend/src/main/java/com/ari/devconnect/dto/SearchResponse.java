package com.ari.devconnect.dto;

import java.util.List;

public class SearchResponse {

    private List<DeveloperResult> developers;
    private List<ProjectResult> projects;

    public SearchResponse() {
    }

    public SearchResponse(List<DeveloperResult> developers,
                          List<ProjectResult> projects) {
        this.developers = developers;
        this.projects = projects;
    }

    public List<DeveloperResult> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<DeveloperResult> developers) {
        this.developers = developers;
    }

    public List<ProjectResult> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectResult> projects) {
        this.projects = projects;
    }

    public static class DeveloperResult {

        private String username;
        private String headline;
        private String skills;
        private String avatarUrl;

        public DeveloperResult() {
        }

        public DeveloperResult(String username,
                               String headline,
                               String skills,
                               String avatarUrl) {
            this.username = username;
            this.headline = headline;
            this.skills = skills;
            this.avatarUrl = avatarUrl;
        }

        public String getUsername() {
            return username;
        }

        public String getHeadline() {
            return headline;
        }

        public String getSkills() {
            return skills;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }
    }

    public static class ProjectResult {

        private Long id;
        private String title;
        private String description;
        private String techStack;
        private String username;

        public ProjectResult() {
        }

        public ProjectResult(Long id,
                             String title,
                             String description,
                             String techStack,
                             String username) {
            this.id = id;
            this.title = title;
            this.description = description;
            this.techStack = techStack;
            this.username = username;
        }

        public Long getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public String getTechStack() {
            return techStack;
        }

        public String getUsername() {
            return username;
        }
    }
}
