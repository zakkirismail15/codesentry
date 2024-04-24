package com.zakkirdev.codesentry.controller.request;

import lombok.Data;

@Data
public class ProjectRequest {

    private Long projectId;
    private String title;
    private String description;
}
