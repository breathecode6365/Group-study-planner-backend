package com.groupStudyPlanner.GroupStudyPlannerHackathon.controller;

import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.request.UpdatePasswordReqDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.request.UserUpdateReqDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.response.*;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.service.UserService;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.utility.CommonConstants;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public GenericResDTO<UserDataResDTO> getMyProfile(Authentication auth) {
         var payload = userService.getMyProfile(auth.getName());
         return new GenericResDTO<>(payload, new BasicResDTO(CommonConstants.PROFILE_FETCH_SUCCESSFUL, HttpStatus.OK));
    }

    @PutMapping
    public BasicResDTO updateMyProfile(Authentication auth, @RequestBody @Valid UserUpdateReqDTO userUpdateReqDTO) {
        return userService.updateMyProfile(userService.getByEmail(auth.getName()), userUpdateReqDTO);
    }
    @PatchMapping("password")
    public BasicResDTO updatePassword(Authentication auth, @RequestBody @Valid UpdatePasswordReqDTO updatePasswordReqDTO) {
        return userService.updatePassword(userService.getByEmail(auth.getName()), updatePasswordReqDTO);
    }
    @GetMapping("groups")
    public GenericResDTO<List<GetAllGroupsResDTO>> getMyGroups(Authentication auth) {
        var payload = userService.getMyGroups(userService.getByEmail(auth.getName()));
        return new GenericResDTO<>(payload, new BasicResDTO(CommonConstants.GROUP_FETCH_SUCCESSFUL, HttpStatus.OK));
    }
    @GetMapping("tasks")
    public GenericResDTO<List<TaskDataResDTO>> getMyTasks(Authentication auth) {
        var payload = userService.getMyTasks(userService.getByEmail(auth.getName()));
        return new GenericResDTO<>(payload, new BasicResDTO(CommonConstants.TASK_FETCH_SUCCESSFUL, HttpStatus.OK));
    }
}
