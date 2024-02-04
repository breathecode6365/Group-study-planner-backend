package com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.repository;

import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserDAO, UUID> {

    Optional<UserDAO> findByEmailId(String emailId);
}
