package com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.repository;

import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.GroupDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface GroupRepository extends JpaRepository<GroupDAO, UUID> {

    Optional<GroupDAO> findByName(String name);
}
