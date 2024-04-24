package com.zakkirdev.codesentry.repository.entity;

import com.zakkirdev.codesentry.repository.sequence.UserSequence;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"}),
        @UniqueConstraint(columnNames = {"email"})
})
public class User {

    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_id_sequence"
    )
    @GenericGenerator(
            name = "user_id_sequence",
            type = UserSequence.class,
            parameters = {
                    @org.hibernate.annotations.Parameter(name = UserSequence.INCREMENT_PARAM, value = "1"),
                    @org.hibernate.annotations.Parameter(name = UserSequence.VALUE_PREFIX_PARAMETER, value = "1"),
                    @org.hibernate.annotations.Parameter(name = UserSequence.NUMBER_FORMAT_PARAMETER, value = "%09d")
            }
    )
    private String id;

    private String username;

    private String password;

    private String email;

    private String firstName;

    private String lastName;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

    public void addRole(Role role){
        this.roles.add(role);
    }
}
