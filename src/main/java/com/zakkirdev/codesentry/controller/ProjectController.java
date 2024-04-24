package com.zakkirdev.codesentry.controller;

import com.zakkirdev.codesentry.controller.request.ProjectRequest;
import com.zakkirdev.codesentry.controller.response.ErrorResponse;
import com.zakkirdev.codesentry.repository.ProjectRepository;
import com.zakkirdev.codesentry.repository.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

import static com.zakkirdev.codesentry.util.error.GeneralError.RESOURCE_NOT_FOUND;
import static com.zakkirdev.codesentry.util.error.GeneralError.SYSTEM_ERROR;

@RestController
@RequestMapping("/api")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @PreAuthorize("hasRole('DEVELOPER')")
    @PostMapping("/project/create")
    public ResponseEntity createProject(@RequestBody ProjectRequest request){
        Project newProject = new Project();
        newProject.setTitle(request.getTitle());
        newProject.setDescription(request.getDescription());

        Project project = projectRepository.save(newProject);
        return ResponseEntity.ok(project);
    }

    @PreAuthorize("hasRole('DEVELOPER')")
    @PostMapping("/project/modify")
    public ResponseEntity modifyProject(@RequestBody ProjectRequest request){
        Project project = projectRepository.findById(request.getProjectId()).orElseThrow();
        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        projectRepository.save(project);
        return ResponseEntity.ok(project);
    }
}
