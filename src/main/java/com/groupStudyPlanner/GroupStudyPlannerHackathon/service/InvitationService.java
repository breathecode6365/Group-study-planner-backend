package com.groupStudyPlanner.GroupStudyPlannerHackathon.service;

import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.GroupDAO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.GroupInvitationDAO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.UserDAO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.request.UserInviteReqDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.response.BasicResDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.response.GenericResDTO;

import java.util.UUID;

public interface InvitationService {

    BasicResDTO inviteToGroup(UserInviteReqDTO userInviteReqDTO, UserDAO userDAO, GroupDAO groupDAO);

    BasicResDTO verifyInvitation(UUID token, UserDAO userDAO);

    BasicResDTO acceptInvite( UUID token);
    BasicResDTO resendInvite(UUID invId, UserDAO userDAO);
}
