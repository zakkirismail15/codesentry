package com.zakkirdev.codesentry.repository.entity;

import com.zakkirdev.codesentry.repository.enums.Priority;
import com.zakkirdev.codesentry.repository.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project projectId;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reportedBy", referencedColumnName = "id")
    private User reportedBy;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "assignedTo", referencedColumnName = "id")
    private User assignedTo;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
