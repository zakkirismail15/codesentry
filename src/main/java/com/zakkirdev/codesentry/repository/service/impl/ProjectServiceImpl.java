package com.zakkirdev.codesentry.repository.service.impl;

import com.zakkirdev.codesentry.controller.request.ProjectRequest;
import com.zakkirdev.codesentry.controller.response.ProjectResponse;
import com.zakkirdev.codesentry.repository.ProjectRepository;
import com.zakkirdev.codesentry.repository.UserRepository;
import com.zakkirdev.codesentry.repository.entity.Project;
import com.zakkirdev.codesentry.repository.entity.ProjectMember;
import com.zakkirdev.codesentry.repository.entity.User;
import com.zakkirdev.codesentry.repository.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public ProjectResponse createProject(ProjectRequest request) {
        Project project = new Project();
        project.setTitle(request.getTitle());
        project.setDescription(request.getDescription());
        project.setOwner(retrieveUser());
        ProjectMember projectMember = new ProjectMember();
        projectMember.setMember(project.getOwner());
        projectMember.setProject(project);

        List<ProjectMember> projectMemberList =  List.of(projectMember);
        project.setMembers(projectMemberList);
        projectRepository.save(project);

        ProjectResponse projectResponse = assembleResponse(project);
        return projectResponse;
    }

    @Override
    public List<ProjectResponse> getProjectByUserId() {
        List<Project> projects = projectRepository.findProjectsByUserId(retrieveUser().getId());
        List<ProjectResponse> projectResponseList = projects.stream().map(ProjectServiceImpl::assembleResponse).toList();
        return projectResponseList;
    }


    private static ProjectResponse assembleResponse(Project project) {
        ProjectResponse projectResponse = new ProjectResponse();
        projectResponse.setId(project.getId());
        projectResponse.setTitle(project.getTitle());
        projectResponse.setDescription(project.getDescription());
        projectResponse.setCreatedAt(project.getCreatedAt().toString());
        projectResponse.setUpdatedAt(project.getUpdateAt().toString());
        projectResponse.setOwner(project.getOwner().getId().toString());
        projectResponse.setMembers(project.getMembers() != null ?
                project.getMembers().stream().map((ProjectMember t) -> t.getMember().getId()).toList()
                : null);
        return projectResponse;
    }

    private User retrieveUser() {
        UsernamePasswordAuthenticationToken userDetails = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        System.out.println(userDetails);
        return userRepository.findByEmail(String.valueOf(userDetails.getPrincipal())).orElse(null);
    }
}
