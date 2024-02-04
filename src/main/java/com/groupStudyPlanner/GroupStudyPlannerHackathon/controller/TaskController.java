package com.groupStudyPlanner.GroupStudyPlannerHackathon.controller;

import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.request.CreateTaskReqDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.response.BasicResDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.service.GroupService;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.service.TaskService;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("task")
public class TaskController {
    private final TaskService taskService;
    private final UserService userService;
    private final GroupService groupService;
    public TaskController(TaskService taskService, UserService userService, GroupService groupService) {

        this.taskService = taskService;
        this.userService = userService;
        this.groupService = groupService;
    }
    @PostMapping("/{gid}")
    public BasicResDTO createTask( @RequestBody @Valid CreateTaskReqDTO createTaskReqDTO, Authentication auth, @PathVariable UUID gid) {
        return taskService.createTask(createTaskReqDTO,userService.findByEmailId(auth.getName()),groupService.findById(gid));
    }
    @GetMapping("/{tid}")
    public BasicResDTO checkTask(Authentication auth, @PathVariable UUID tid) {
        return taskService.checkTask(userService.findByEmailId(auth.getName()), tid);
    }


}
