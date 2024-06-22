package com.zakkirdev.codesentry.repository.service;

import com.zakkirdev.codesentry.controller.request.ProjectRequest;
import com.zakkirdev.codesentry.controller.response.ProjectResponse;
import com.zakkirdev.codesentry.repository.entity.Project;

import java.util.List;

public interface ProjectService {
    
    ProjectResponse createProject(ProjectRequest request);

    List<ProjectResponse> getProjectByUserId();
}
