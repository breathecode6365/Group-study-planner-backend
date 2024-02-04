package com.groupStudyPlanner.GroupStudyPlannerHackathon.service.implementations;

import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.GroupDAO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.TaskDAO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.UserDAO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.repository.TaskRepository;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.request.CreateTaskReqDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.response.BasicResDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.enumerators.TaskStatus;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.exception.ApiRuntimeException;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.service.GroupService;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskService {
    private final GroupService groupService;
    private final TaskRepository taskRepository;

    public TaskServiceImpl(GroupService groupService, TaskRepository taskRepository) {
        this.groupService = groupService;
        this.taskRepository = taskRepository;
    }

    @Override
    public BasicResDTO createTask(CreateTaskReqDTO createTaskReqDTO, UserDAO userDAO, GroupDAO groupDAO) {
        if(!groupService.verifyMember(userDAO, groupDAO)){
            return new BasicResDTO("You are not a member of this group", HttpStatus.BAD_REQUEST);
        }
        TaskDAO taskDAO = TaskDAO.builder()
                .name(createTaskReqDTO.taskName())
                .description(createTaskReqDTO.taskDescription())
                .deadline(createTaskReqDTO.taskDeadline())
                .groupId(groupDAO)
                .build();
        taskRepository.save(taskDAO);
        groupService.evaluateOverAllProgress(groupDAO);
        return new BasicResDTO("Task created successfully", HttpStatus.CREATED);
    }

    @Transactional
    public BasicResDTO checkTask(UserDAO userDAO, UUID tid) {
        TaskDAO taskDAO = taskRepository.findById(tid)
                .orElseThrow(() -> new ApiRuntimeException("Task not found", HttpStatus.NOT_FOUND));
        LocalDateTime taskCreatedOn = taskDAO.getCreatedOn();
        LocalDateTime taskDeadline = taskCreatedOn.plusDays(taskDAO.getDeadline());
        if(LocalDateTime.now().isAfter(taskDeadline)){
            taskDAO.setStatus(TaskStatus.OVERDUE);
            taskDAO = taskRepository.save(taskDAO);
        };
        if (taskDAO.getCompletedList().contains(userDAO)) {
            //remove user from completed list
            taskDAO.getCompletedList().remove(userDAO);
            taskDAO = taskRepository.save(taskDAO);
        }
        else {
            //add user to completed list
            taskDAO.getCompletedList().add(userDAO);
            taskDAO = taskRepository.save(taskDAO);
        }
        evaluateTaskProgress(taskDAO, userDAO);
        return new BasicResDTO("Task checked", HttpStatus.OK);
    }

    private void evaluateTaskProgress(TaskDAO taskDAO, UserDAO userDAO) {
        long totalMembers = groupService.getMembers(taskDAO.getGroupId().getGid(),userDAO).stream().count();
        long completedMembers = taskDAO.getCompletedList().stream().count();
        taskDAO.setProgress((int) ((completedMembers/totalMembers)*100));
        if(completedMembers == totalMembers){
            taskDAO.setStatus(TaskStatus.COMPLETED);
            taskDAO = taskRepository.save(taskDAO);
        }
        groupService.evaluateOverAllProgress(taskDAO.getGroupId());
    }
}
