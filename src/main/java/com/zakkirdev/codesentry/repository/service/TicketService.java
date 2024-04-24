package com.zakkirdev.codesentry.repository.service;

import com.zakkirdev.codesentry.repository.entity.Ticket;
import com.zakkirdev.codesentry.controller.request.TicketRequest;


public interface TicketService {

    public Ticket createAndAssignTicket(TicketRequest request);

    public Ticket updateTicket(TicketRequest request);
}
