package com.ari.devconnect.model;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

  
@Entity
@Table(name="projects")
public class Project {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition="TEXT",nullable=false)
    private String description;
    private String title;
    private String techStack;
    private String githubUrl;
    private String demoUrl;
    private LocalDateTime createdAt=LocalDateTime.now();
    @ManyToOne
    @JoinColumn(name="user_id",nullable=false)
    private User user;
    public Project() {}
    public  Project(Long id,String title,String description,String techStack,String githubUrl,String demoUrl){
        this.id=id;
        this.title=title;
        this.description=description;
        this.techStack=techStack;
        this.githubUrl=githubUrl;
        this.demoUrl=demoUrl;

    }
     public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Long getId(){
        return id;
    }
    public String getDescription(){
        return description;
    }
    public String getTechStack(){
        return techStack;
    }
    public String getGithubUrl(){
        return githubUrl;
    }
    public void setId(Long id)
    {
        this.id=id;
    }
    public void setDescription(String description){
        this.description=description;
    }
    public void setTechStack(String techStack)
    {
        this.techStack=techStack;
    }
    public void setGithubUrl(String githubUrl)
    {
        this.githubUrl=githubUrl;
    }
     public String getDemoUrl() { return demoUrl; }
    public void setDemoUrl(String demoUrl) { this.demoUrl = demoUrl; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
        public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}


    




    

