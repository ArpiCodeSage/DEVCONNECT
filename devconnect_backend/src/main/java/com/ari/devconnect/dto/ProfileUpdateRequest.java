package com.ari.devconnect.dto;

public class ProfileUpdateRequest {
     private String headline;
    private String githubUrl;
    private String linkedinUrl;
    private String websiteUrl;
    private String avatarUrl;
    private String bio;
    private String skills;
    public ProfileUpdateRequest(){}
    public ProfileUpdateRequest(String headline,String bio,String skills,String githubUrl,String websiteUrl,String avatarUrl,String linkedlnUrl)
{
    this.headline=headline;
    this.websiteUrl=websiteUrl;
    this.linkedinUrl=linkedlnUrl;
    this.githubUrl=githubUrl;
    this.skills=skills;
    this.bio=bio;
}
  
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
