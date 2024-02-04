package com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.repository;

import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.GroupDAO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.UserDAO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.UserToGroupMapDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserToGroupMapRepository extends JpaRepository<UserToGroupMapDAO, UUID>{

    List<UserToGroupMapDAO> findAllByUserId(UserDAO userDAO);
    List<UserToGroupMapDAO> findByGroupId(GroupDAO groupDAO);
}
