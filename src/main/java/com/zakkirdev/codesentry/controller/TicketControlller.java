package com.zakkirdev.codesentry.controller;


import com.zakkirdev.codesentry.controller.request.TicketRequest;
import com.zakkirdev.codesentry.controller.response.TicketResponse;
import com.zakkirdev.codesentry.repository.entity.Ticket;
import com.zakkirdev.codesentry.repository.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TicketControlller {

    @Autowired
    TicketService ticketService;

    @PostMapping("/ticket/create")
    @PreAuthorize("hasRole('TESTER')")
    public ResponseEntity createTicket(@RequestBody TicketRequest request){
        Ticket ticket = ticketService.createAndAssignTicket(request);
        TicketResponse response = new TicketResponse();
        return assembleResponse(response, ticket);
    }

    @PostMapping("/ticket/modify")
    @PreAuthorize("hasRole('TESTER')")
    public ResponseEntity modifyTicket(@RequestBody TicketRequest request){
        Ticket ticket = ticketService.updateTicket(request);
        TicketResponse response = new TicketResponse();
        return assembleResponse(response, ticket);
    }

    private ResponseEntity<TicketResponse> assembleResponse(TicketResponse response, Ticket ticket) {
        response.setId(ticket.getId());
        response.setProjectId(ticket.getProjectId().getId());
        response.setTitle(ticket.getTitle());
        response.setDescription(ticket.getDescription());
        response.setStatus(ticket.getStatus());
        response.setPriority(ticket.getPriority());
        response.setReportedBy(ticket.getReportedBy().getId());
        response.setAssignedTo(ticket.getAssignedTo().getId());
        return ResponseEntity.ok(response);
    }
}
