package com.ari.devconnect.dto;

import java.time.LocalDateTime;

public class BlogCommentResponse {

    private Long id;
    private String content;
    private String username;
    private LocalDateTime createdAt;

    public BlogCommentResponse() {
    }

    public BlogCommentResponse(
            Long id,
            String content,
            String username,
            LocalDateTime createdAt
    ) {
        this.id = id;
        this.content = content;
        this.username = username;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getUsername() {
        return username;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}