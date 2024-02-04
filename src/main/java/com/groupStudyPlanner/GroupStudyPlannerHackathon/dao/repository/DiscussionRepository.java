package com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.repository;

import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.DiscussionDAO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.GroupDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DiscussionRepository extends JpaRepository<DiscussionDAO, UUID> {
    List<DiscussionDAO> findAllByGroupId(GroupDAO groupDAO);
}
