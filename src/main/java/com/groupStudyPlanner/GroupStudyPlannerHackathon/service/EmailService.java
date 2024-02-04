package com.groupStudyPlanner.GroupStudyPlannerHackathon.service;

import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.GroupInvitationDAO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.UserDAO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.response.BasicResDTO;

public interface EmailService {
    BasicResDTO sendInvitationEmail(GroupInvitationDAO groupInvitationDAO, UserDAO Sender, UserDAO Receiver);
}
