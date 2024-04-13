package com.zakkirdev.codesentry.repository;

import com.zakkirdev.codesentry.repository.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project,Long> {

}
