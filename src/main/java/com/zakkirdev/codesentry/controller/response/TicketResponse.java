package com.zakkirdev.codesentry.controller.response;

import com.zakkirdev.codesentry.repository.enums.Priority;
import com.zakkirdev.codesentry.repository.enums.Status;
import lombok.Data;

@Data
public class TicketResponse {

    private Long id;
    private Long projectId;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    private String reportedBy;
    private String assignedTo;
}
