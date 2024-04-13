package com.zakkirdev.codesentry.repository.entity;

import com.zakkirdev.codesentry.repository.enums.AccessRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 60)
    @Enumerated(EnumType.STRING)
    private AccessRole name;

    @Override
    public String toString() {
        return name.toString();
    }
}
