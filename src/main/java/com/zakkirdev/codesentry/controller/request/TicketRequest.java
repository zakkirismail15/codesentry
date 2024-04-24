package com.zakkirdev.codesentry.controller.request;

import lombok.Data;

@Data
public class TicketRequest {

    private Long id;
    private Long projectId;
    private String title;
    private String description;
    private String status;
    private String priority;
    private Long reportedBy;
    private Long assignedTo;
}
