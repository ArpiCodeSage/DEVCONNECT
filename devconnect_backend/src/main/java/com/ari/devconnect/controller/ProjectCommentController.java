package com.ari.devconnect.controller;

import com.ari.devconnect.dto.ProjectCommentRequest;
import com.ari.devconnect.dto.ProjectCommentResponse;
import com.ari.devconnect.service.ProjectCommentService;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectCommentController {

    private final ProjectCommentService projectCommentService;

    public ProjectCommentController(
            ProjectCommentService projectCommentService
    ) {
        this.projectCommentService = projectCommentService;
    }

    @PostMapping("/{projectId}/comments")
    public ResponseEntity<ProjectCommentResponse> addComment(
            @PathVariable Long projectId,
            @RequestBody ProjectCommentRequest request,
            @AuthenticationPrincipal UserDetails userDetails
    ) {

        String username = userDetails.getUsername();

        ProjectCommentResponse response =
                projectCommentService.addComment(
                        projectId,
                        username,
                        request
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{projectId}/comments")
    public ResponseEntity<List<ProjectCommentResponse>> getComments(
            @PathVariable Long projectId
    ) {

        List<ProjectCommentResponse> comments =
                projectCommentService.getComments(projectId);

        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<String> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {

        String username = userDetails.getUsername();

        projectCommentService.deleteComment(
                commentId,
                username
        );

        return ResponseEntity.ok(
                "Comment deleted successfully"
        );
    }
}