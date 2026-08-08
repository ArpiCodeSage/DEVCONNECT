//Sends complete profile details (headline, bio, skills, URLs, username, email) back to React.
// Imagine a user in React visits a developer's portfolio page: devconnect.com/profile/ari_dev.

// React needs to display Ari's portfolio details on the screen (Headline, Bio, Skills, GitHub link, Email, Username).

// ProfileResponse.java is the Formatted Delivery Box sent from Spring Boot back to React over the web!
// It takes data from 2 different database tables (users table + profiles table) and packs them neatly into ONE JSON object that React can easily render on screen.


package com.ari.devconnect.dto;

public class ProfileResponse {
    private String username;
    private String email;
    private String headline;
    private String githubUrl;
    private String linkedinUrl;
    private String websiteUrl;
    private String avatarUrl;
    private String bio;
    private String skills;
    public ProfileResponse(){}
    public ProfileResponse(String username,String email,String headline,String bio,String skills,String githubUrl,String websiteUrl,String avatarUrl,String linkedlnUrl)
{
    this.username=username;
    this.email=email;
    this.headline=headline;
    this.websiteUrl=websiteUrl;
    this.linkedinUrl=linkedlnUrl;
    this.githubUrl=githubUrl;
    this.skills=skills;
    this.bio=bio;
    this.headline=headline;
    this.avatarUrl=avatarUrl;
}
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getHeadline() { return headline; }
    public void setHeadline(String headline) { this.headline = headline; }
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

   
}
