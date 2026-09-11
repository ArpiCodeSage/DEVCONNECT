package com.ari.devconnect.controller;
import com.ari.devconnect.dto.ProjectLikeResponse;
import com.ari.devconnect.service.ProjectLikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
public class ProjectLikeController {

    private final ProjectLikeService projectLikeService;

    public ProjectLikeController(
            ProjectLikeService projectLikeService
    ) {
        this.projectLikeService = projectLikeService;
    }

    @PostMapping("/{projectId}/like")
    public ResponseEntity<ProjectLikeResponse> toggleLike(
            @PathVariable Long projectId,
            @AuthenticationPrincipal UserDetails userDetails
    ) {

        String username = userDetails.getUsername();

        ProjectLikeResponse response =
                projectLikeService.toggleLike(
                        projectId,
                        username
                );

        return ResponseEntity.ok(response);
    }
    @GetMapping("/{projectId}/like")
public ResponseEntity<ProjectLikeResponse> getLikeStatus(
        @PathVariable Long projectId,
        @AuthenticationPrincipal UserDetails userDetails
) {

    String username = userDetails.getUsername();

    ProjectLikeResponse response =
            projectLikeService.getLikeStatus(
                    projectId,
                    username
            );

    return ResponseEntity.ok(response);
}
}
