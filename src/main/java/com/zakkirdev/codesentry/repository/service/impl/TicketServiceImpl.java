package com.zakkirdev.codesentry.repository.service.impl;

import com.zakkirdev.codesentry.repository.UserRepository;
import com.zakkirdev.codesentry.controller.request.TicketRequest;
import com.zakkirdev.codesentry.repository.ProjectRepository;
import com.zakkirdev.codesentry.repository.TicketRepository;
import com.zakkirdev.codesentry.repository.entity.Project;
import com.zakkirdev.codesentry.repository.entity.Ticket;
import com.zakkirdev.codesentry.repository.entity.User;
import com.zakkirdev.codesentry.repository.enums.Priority;
import com.zakkirdev.codesentry.repository.enums.Status;
import com.zakkirdev.codesentry.repository.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Override
    public Ticket createAndAssignTicket(TicketRequest request) {
        Ticket ticket = new Ticket();
        assemblingTicket(ticket,request);
        ticketRepository.save(ticket);
        return ticket;
    }

    @Override
    public Ticket updateTicket(TicketRequest request) {
        Ticket ticket = ticketRepository.findById(request.getId()).orElse(null);
        if(ticket == null){
            throw new NoSuchElementException("Ticket not found");
        }
        assemblingTicket(ticket, request);
        ticketRepository.save(ticket);
        return ticket;
    }


    private void assemblingTicket(Ticket ticket, TicketRequest request){
        // 1. Get project resources
        Project project = projectRepository.findById(request.getProjectId()).orElse(null);
        if(project == null){
            throw new NoSuchElementException("Project not found");
        }
        // 2. Get assignedTo (Optional) and reportedBy (Mandatory)
        User reportedBy = userRepository.findById(request.getReportedBy()).orElse(null);
        User assignedTo = userRepository.findById(request.getAssignedTo()).orElse(null);
        if(reportedBy == null){
            throw new NoSuchElementException("Required reporting user");
        }

        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setProjectId(project);
        ticket.setStatus(Status.OPEN);
        ticket.setPriority(Priority.getPriorityEnum(request.getPriority()));
        ticket.setReportedBy(reportedBy);
        ticket.setAssignedTo(assignedTo);
    }
}
