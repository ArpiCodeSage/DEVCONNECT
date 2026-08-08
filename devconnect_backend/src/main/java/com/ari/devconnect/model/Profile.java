package com.ari.devconnect.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
 @Entity
 @Table(name="profiles")
public class Profile {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String headline;//we only write @Column when we need to enforce rules,change type or rename; the column is automatically created anyway
    @Column(columnDefinition="TEXT")//ells JPA and Hibernate to override the default SQL data type mapping for a database column and explicitly create it as a TEXT type.
    //since String has default 255 characters limit
    private String bio;
    private String skills;
    private String githubUrl;
    private String linkedinUrl;
    private String websiteUrl;
    private String avatarUrl;
    @OneToOne
    @JoinColumn(name="user_id",nullable=false)
    private User user;
    public Profile(){}
    public Long getId(){
        return id;

    }
    public void setId() {
        this.id=id;
    }
    public String getHeadline() {
        return headline;
    }
    public void setHeadline(String headline) {
        this.headline=headline;
    }
     public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public String getSkills() { return skills; }
    public void setSkills(String skills) { this.skills = skills; }
    public String getGithubUrl() { return githubUrl; }
    public void setGithubUrl(String githubUrl) { this.githubUrl = githubUrl; }
    public String getLinkedinUrl() { return linkedinUrl; }
    public void setLinkedinUrl(String linkedinUrl) { this.linkedinUrl = linkedinUrl; }
    public String getWebsiteUrl() { return websiteUrl; }
    public void setWebsiteUrl(String websiteUrl) { this.websiteUrl = websiteUrl; }
    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }




    
}
