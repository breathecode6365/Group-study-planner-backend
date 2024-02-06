package com.groupStudyPlanner.GroupStudyPlannerHackathon.controller;

import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.request.UserInviteReqDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.response.BasicResDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.service.GroupService;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.service.InvitationService;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.service.UserService;
import jakarta.persistence.Basic;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("invitation")
public class    InvitationController {
    private final UserService userService;
    private final GroupService groupService;
    private final InvitationService invitationService;
    public InvitationController(UserService userService, GroupService groupService, InvitationService invitationService) {
        this.userService = userService;
        this.groupService = groupService;
        this.invitationService = invitationService;
    }
    @PostMapping("/{gid}")
    public BasicResDTO sendInvitation(@PathVariable UUID gid, Authentication auth, @RequestBody @Valid UserInviteReqDTO userInviteReqDTO) {
        return invitationService.inviteToGroup(userInviteReqDTO,userService.findByEmailId(auth.getName()),groupService.findById(gid));
    }
    @GetMapping("verify/{token}")
    public BasicResDTO verifyInvitation(@PathVariable UUID token, Authentication auth) {
        return invitationService.verifyInvitation(token,userService.findByEmailId(auth.getName()));
    }


}
