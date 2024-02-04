package com.groupStudyPlanner.GroupStudyPlannerHackathon.service;

import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.GroupDAO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.TaskDAO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.UserDAO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.request.CreateTaskReqDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.response.BasicResDTO;

import java.time.LocalDateTime;
import java.util.UUID;

public interface TaskService {
    BasicResDTO createTask(CreateTaskReqDTO createTaskReqDTO, UserDAO userDAO, GroupDAO groupDAO);
    BasicResDTO checkTask(UserDAO userDAO, UUID tid);
}
