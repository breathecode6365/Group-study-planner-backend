package com.groupStudyPlanner.GroupStudyPlannerHackathon.controller;

import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.DiscussionDAO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.GroupDAO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.TaskDAO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.UserDAO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.request.CreateForumReqDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.request.CreateGroupReqDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.request.GroupUpdateReqDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.request.ProductReqDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.response.*;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.exception.ApiRuntimeException;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.service.GroupService;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.service.UserService;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.utility.CommonConstants;
import jakarta.validation.Valid;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("group")
public class GroupController {
    private final UserService userService;
    private final GroupService groupService;

    public GroupController(UserService userService, GroupService groupService) {
        this.userService = userService;
        this.groupService = groupService;
    }
    @GetMapping("/{gid}")
    public GenericResDTO<GetAllGroupsResDTO> getGroup(@PathVariable UUID gid, Authentication auth) {
        UserDAO user = userService.findByEmailId(auth.getName());
        GroupDAO groupDAO = groupService.findById(gid);
        if(!groupService.verifyMember(user, groupDAO)){
            throw new ApiRuntimeException(CommonConstants.UNAUTHORISED_OPERATION, HttpStatus.UNAUTHORIZED);
        }
        var payload = new GetAllGroupsResDTO(groupDAO.getGid() ,groupDAO.getName(), groupDAO.getDescription(), new UserDataResDTO( groupDAO.getCreatedBy().getName(), groupDAO.getCreatedBy().getEmailId(), groupDAO.getCreatedBy().getProfilePic()));
        return new GenericResDTO<>(payload, new BasicResDTO("Group fetched successfully!", HttpStatus.ACCEPTED));
    }
    @GetMapping("members/{gid}")
    public GenericResDTO<List<UserDataResDTO>> getGroupMembers(@PathVariable UUID gid, Authentication auth) {
        var payload = groupService.getMembers(gid, userService.findByEmailId(auth.getName()));
        return new GenericResDTO<>(payload, new BasicResDTO("Members fetched successfully!", HttpStatus.ACCEPTED));
    }

    @PostMapping
    public BasicResDTO createGroup(Authentication auth, @RequestBody @Valid CreateGroupReqDTO createGroupReqDTO) {
        UserDAO user = userService.findByEmailId(auth.getName());
        return groupService.createGroup(user, createGroupReqDTO);
    }

    @PutMapping("/{gid}")
    public BasicResDTO updateGroup(@PathVariable UUID gid, Authentication auth, @RequestBody @Valid GroupUpdateReqDTO groupUpdateReqDTO) {
        UserDAO user = userService.findByEmailId(auth.getName());
        return groupService.updateGroup(user, groupUpdateReqDTO, gid);
    }
    @GetMapping("tasks/{gid}")
    @Transactional
    public GenericResDTO<List<TaskDataResDTO>> getAllTasks(@PathVariable UUID gid, Authentication auth) {
        UserDAO user = userService.findByEmailId(auth.getName());
        GroupDAO groupDAO = groupService.findById(gid);
        if(!groupService.verifyMember(user, groupDAO)){
            throw new ApiRuntimeException(CommonConstants.UNAUTHORISED_OPERATION, HttpStatus.UNAUTHORIZED);
        }
        return new GenericResDTO<>(groupService.getTasks(gid), new BasicResDTO("Tasks fetched successfully!", HttpStatus.ACCEPTED));
    }

    @PostMapping("/resource/{gid}")
    public BasicResDTO handleFileUpload(@PathVariable UUID gid, @RequestBody ProductReqDTO productReqDTO  , Authentication auth) {
        UserDAO user = userService.findByEmailId(auth.getName());
        GroupDAO groupDAO = groupService.findById(gid);
        return groupService.addResource(productReqDTO, user, groupDAO);
    }
    @GetMapping("resource/{gid}")
    public GenericResDTO<List<ProductDataResDTO>> getResources(@PathVariable UUID gid, Authentication auth) {
        UserDAO user = userService.findByEmailId(auth.getName());
        GroupDAO groupDAO = groupService.findById(gid);
        if(!groupService.verifyMember(user, groupDAO)){
            throw new ApiRuntimeException(CommonConstants.UNAUTHORISED_OPERATION, HttpStatus.UNAUTHORIZED);
        }
        return new GenericResDTO<>(groupService.getResources(groupDAO), new BasicResDTO("Resources fetched successfully!", HttpStatus.ACCEPTED));
    }
    @PostMapping("forum/{gid}")
    public BasicResDTO createForum(@PathVariable UUID gid, Authentication auth, @RequestBody @Valid CreateForumReqDTO createForumReqDTO) {
        UserDAO user = userService.findByEmailId(auth.getName());
        GroupDAO groupDAO = groupService.findById(gid);
        if(!groupService.verifyMember(user, groupDAO)){
            throw new ApiRuntimeException(CommonConstants.UNAUTHORISED_OPERATION, HttpStatus.UNAUTHORIZED);
        }
        return userService.createForum(user, groupDAO, createForumReqDTO);
    }

    @GetMapping("forum/{gid}")
    public GenericResDTO<List<DiscussionDAO>> getForum(@PathVariable UUID gid, Authentication auth) {
        UserDAO user = userService.findByEmailId(auth.getName());
        GroupDAO groupDAO = groupService.findById(gid);
        if(!groupService.verifyMember(user, groupDAO)){
            throw new ApiRuntimeException(CommonConstants.UNAUTHORISED_OPERATION, HttpStatus.UNAUTHORIZED);
        }
        return userService.getForum(groupDAO);

    }


}
