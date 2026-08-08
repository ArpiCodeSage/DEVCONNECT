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
    private String techStack;
    private String githubUrl;
    private String demoUrl;
    private LocalDateTime createdAt=LocalDateTime.now();
    @ManyToOne
    @JoinColumn(name="user_id",nullable=false)
    private User user;
    public Project() {}
    public void Project(Long id,String description,String techStack,String githubIrl,String demoUrl){
        this.id=id;
        this.description=description;
        this.techStack=techStack;
        this.githubUrl=githubUrl;
        this.demoUrl=demoUrl;

    }
    public Long getId(){
        return id;
    }
    public String getDesc(){
        return description;
    }
    public String getTechStack(){
        return techStack;
    }
    public String getGithubUrl(){
        return githubUrl;
    }
     public String getDemoUrl() { return demoUrl; }
    public void setDemoUrl(String demoUrl) { this.demoUrl = demoUrl; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
        public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}


    




    

