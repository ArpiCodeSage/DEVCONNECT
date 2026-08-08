package com.ari.devconnect.controller;
import com.ari.devconnect.dto.ProjectRequest;
import com.ari.devconnect.dto.ProjectResponse;
import com.ari.devconnect.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;//mports Web Annotations (@RestController, @RequestMapping, @PostMapping, @GetMapping, @DeleteMapping, @PathVariable, @RequestBody) 
import java.util.List;

@RestController//This class handles RESTful HTTP requests and automatically converts Java return objects into JSON strings
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectService projectService;
    public ProjectController(ProjectService projectService)
    {
        this.projectService=projectService;
    }
    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@AuthenticationPrincipal UserDetails userDetails, @RequestBody ProjectRequest request)
    {
        String username=userDetails.getUsername();
        ProjectResponse project=projectService.createProject(username,request);
        return ResponseEntity.ok(project);

    }
    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getAllProjects(){
        List<ProjectResponse> projects=projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }
    @GetMapping("/user/{username}")
    public ResponseEntity<List<ProjectResponse>> getProjectsByUsername(@PathVariable String username){
        List<ProjectResponse> projects=projectService.getProjectsByUsername(username);
        return ResponseEntity.ok(projects);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable Long id,@AuthenticationPrincipal UserDetails userDetails){
        String username=userDetails.getUsername();
        projectService.deleteProject(id,username);
        return ResponseEntity.ok("Project deleted successfully");
    }


} 

