package com.zakkirdev.codesentry.repository;

import com.zakkirdev.codesentry.repository.entity.Role;
import com.zakkirdev.codesentry.repository.enums.AccessRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(AccessRole name);
}
