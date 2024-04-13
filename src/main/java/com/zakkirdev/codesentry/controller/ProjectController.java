package com.zakkirdev.codesentry.controller;

import com.zakkirdev.codesentry.controller.request.ProjectReq;
import com.zakkirdev.codesentry.controller.response.ErrorResponse;
import com.zakkirdev.codesentry.exception.ErrorCode;
import com.zakkirdev.codesentry.repository.ProjectRepository;
import com.zakkirdev.codesentry.repository.entity.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProjectController {

    @Autowired
    private ProjectRepository projectRepository;

    @PostMapping("/project/create")
    public ResponseEntity createProject(@RequestBody ProjectReq request){
        Project newProject = new Project();
        newProject.setTitle(request.getTitle());
        newProject.setDescription(request.getDescription());

        try{
            Project project = projectRepository.save(newProject);
            return ResponseEntity.ok(project);
        }catch (Exception e){
            ErrorResponse response = new ErrorResponse(ErrorCode.SYSTEM_ERROR.errorCode,ErrorCode.SYSTEM_ERROR.description);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
