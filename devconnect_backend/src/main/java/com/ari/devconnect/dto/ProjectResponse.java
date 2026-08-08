package com.ari.devconnect.dto;

import java.time.LocalDateTime;

public class ProjectResponse {
    private Long id;
    private String title;
    private String description;
    private String techStack;
    private String githubUrl;
    private String demoUrl;
    private LocalDateTime createdAt;
    private String username; // Username of developer who posted this project

    public ProjectResponse() {}

    public ProjectResponse(Long id, String title, String description, String techStack, 
                           String githubUrl, String demoUrl, LocalDateTime createdAt, String username) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.techStack = techStack;
        this.githubUrl = githubUrl;
        this.demoUrl = demoUrl;
        this.createdAt = createdAt;
        this.username = username;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getTechStack() { return techStack; }
    public void setTechStack(String techStack) { this.techStack = techStack; }

    public String getGithubUrl() { return githubUrl; }
    public void setGithubUrl(String githubUrl) { this.githubUrl = githubUrl; }

    public String getDemoUrl() { return demoUrl; }
    public void setDemoUrl(String demoUrl) { this.demoUrl = demoUrl; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}
