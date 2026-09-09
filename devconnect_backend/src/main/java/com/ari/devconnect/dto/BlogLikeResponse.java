package com.ari.devconnect.dto;

public class BlogLikeResponse {

    private long likeCount;
    private boolean likedByCurrentUser;

    public BlogLikeResponse() {
    }

    public BlogLikeResponse(
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