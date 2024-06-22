package com.zakkirdev.codesentry.repository;

import com.zakkirdev.codesentry.repository.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project,Long> {

    @Query(value = "SELECT p FROM Project p JOIN p.members pm WHERE pm.member.id = :userId")
    List<Project> findProjectsByUserId(String userId);

}
