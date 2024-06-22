package com.zakkirdev.codesentry.controller;

import com.zakkirdev.codesentry.controller.request.ProjectRequest;
import com.zakkirdev.codesentry.controller.response.ProjectResponse;
import com.zakkirdev.codesentry.repository.ProjectRepository;
import com.zakkirdev.codesentry.repository.entity.Project;
import com.zakkirdev.codesentry.repository.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    ProjectService projectService;

    @PreAuthorize("hasRole('PROJECT_MANAGER')")
    @PostMapping("/project/create")
    public ResponseEntity createProject(@RequestBody ProjectRequest request){
        ProjectResponse response = projectService.createProject(request);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('PROJECT_MANAGER')")
    @PostMapping("/project/modify")
    public ResponseEntity modifyProject(@RequestBody ProjectRequest request){
        Project project = projectRepository.findById(request.getProjectId()).orElseThrow();
        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        projectRepository.save(project);
        return ResponseEntity.ok(project);
    }

    @PreAuthorize("hasRole('PROJECT_MANAGER')")
    @GetMapping("/project/{id}")
    public ResponseEntity getProject(@PathVariable Long id){
        Project project = projectRepository.findById(id).orElseThrow();
        return ResponseEntity.ok(project);
    }

    @PreAuthorize("hasRole('PROJECT_MANAGER')")
    @GetMapping("/projects")
    public ResponseEntity getProjects(){
        return ResponseEntity.ok(projectRepository.findAll());
    }


    @PreAuthorize("hasRole('PROJECT_MANAGER')")
    @PostMapping("/project/remove")
    public ResponseEntity removeProject(@RequestBody ProjectRequest request){
        projectRepository.deleteById(request.getProjectId());
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('PROJECT_MANAGER')")
    @GetMapping("/project/user")
    public ResponseEntity getProjectByUserId(){
        return ResponseEntity.ok(projectService.getProjectByUserId());
    }


}
