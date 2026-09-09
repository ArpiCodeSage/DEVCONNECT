package com.ari.devconnect.dto;

public class ProjectLikeResponse {
    private long likeCount;
    private boolean likedByCurrentUser;

    public ProjectLikeResponse() {
    }

    public ProjectLikeResponse(
            long likeCount,
            boolean likedByCurrentUser
    ) {
        this.likeCount = likeCount;
        this.likedByCurrentUser = likedByCurrentUser;
    }

    public long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(long likeCount) {
        this.likeCount = likeCount;
    }

    public boolean isLikedByCurrentUser() {
        return likedByCurrentUser;
    }

    public void setLikedByCurrentUser(boolean likedByCurrentUser) {
        this.likedByCurrentUser = likedByCurrentUser;
}
}
