package com.zakkirdev.codesentry.controller.response;

import lombok.Data;

import java.util.List;

@Data
public class ProjectResponse {

    private Long id;
    private String title;
    private String description;
    private String createdAt;
    private String updatedAt;
    private String owner;
    private List<String> members;
}
