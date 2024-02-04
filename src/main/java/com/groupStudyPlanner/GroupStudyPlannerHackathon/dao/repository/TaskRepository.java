package com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.repository;

import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.GroupDAO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.TaskDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<TaskDAO, UUID> {
    List<TaskDAO> findAllByGroupId(GroupDAO groupDAO);
}
