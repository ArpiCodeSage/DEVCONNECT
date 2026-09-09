package com.ari.devconnect.model;
import jakarta.persistence.*;

@Entity
@Table(
    name = "project_likes",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "project_id"})
    }
)
public class ProjectLike {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    public ProjectLike() {
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
