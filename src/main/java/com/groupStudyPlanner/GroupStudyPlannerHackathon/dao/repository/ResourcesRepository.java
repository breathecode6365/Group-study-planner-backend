package com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.repository;

import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.GroupDAO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.ResourcesDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ResourcesRepository extends JpaRepository<ResourcesDAO, UUID> {
    List<ResourcesDAO> findAllByGroupId(GroupDAO groupDAO);
}
