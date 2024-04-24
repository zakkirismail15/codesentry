package com.zakkirdev.codesentry.repository;

import com.zakkirdev.codesentry.repository.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket,Long> {
}
