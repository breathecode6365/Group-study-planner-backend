package com.groupStudyPlanner.GroupStudyPlannerHackathon.service.implementations;

import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.GroupDAO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.GroupInvitationDAO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.UserDAO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.UserToGroupMapDAO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.repository.GroupInvitationRepository;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.repository.UserToGroupMapRepository;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.request.UserInviteReqDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.response.BasicResDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.enumerators.InvitationStatus;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.exception.ApiRuntimeException;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.service.EmailService;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.service.GroupService;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.service.InvitationService;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.service.UserService;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.utility.CommonConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class InvitationServiceImpl implements InvitationService {

    private final GroupInvitationRepository groupInvitationRepository;
    private final UserToGroupMapRepository userToGroupMapRepository;
    private final UserService userService;
    private final GroupService groupService;
    private final EmailService emailService;
    public InvitationServiceImpl(GroupInvitationRepository groupInvitationRepository, UserToGroupMapRepository userToGroupMapRepository, UserService userService, GroupService groupService, EmailService emailService) {
        this.groupInvitationRepository = groupInvitationRepository;
        this.userToGroupMapRepository = userToGroupMapRepository;
        this.userService = userService;
        this.groupService = groupService;
        this.emailService = emailService;
    }

    public BasicResDTO inviteToGroup(UserInviteReqDTO userInviteReqDTO, UserDAO userDAO, GroupDAO groupDAO) {
        List<GroupInvitationDAO> existingInvitation = groupInvitationRepository.findAllByInvitedTo(groupDAO);
        if (existingInvitation.stream().anyMatch(inv -> inv.getReceiver().getEmailId().equals(userInviteReqDTO.emailId()))) {
            throw new ApiRuntimeException(CommonConstants.INVITATION_ALREADY_SENT, HttpStatus.BAD_REQUEST);
        }
        UserDAO existingUser = userService.findByEmailId(userInviteReqDTO.emailId());
        List<UserToGroupMapDAO> data= userToGroupMapRepository.findByGroupId(groupDAO);
        List<UserDAO> existingUsers = new ArrayList<>();
        for (UserToGroupMapDAO userToGroupMapDAO : data) {
            existingUsers.add(userToGroupMapDAO.getUserId());
        }
        if (existingUsers.stream().anyMatch(user -> user.getUid().equals(existingUser.getUid()))) {
                throw new ApiRuntimeException(CommonConstants.USER_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
            }
        GroupInvitationDAO groupInvitationDAO = GroupInvitationDAO.builder()
                .receiver(existingUser)
                .invitedTo(groupDAO)
                .sender(userDAO)
                .build();
        groupInvitationRepository.save(groupInvitationDAO);
        return emailService.sendInvitationEmail(groupInvitationDAO, userDAO, existingUser);

    }

    public BasicResDTO verifyInvitation(UUID token, UserDAO userDAO) {
        GroupInvitationDAO groupInvitationDAO = groupInvitationRepository.findById(token)
                .orElseThrow(() -> new ApiRuntimeException(CommonConstants.INVITATION_NOT_FOUND, HttpStatus.NOT_FOUND));
        if(groupInvitationDAO.getStatus().equals(InvitationStatus.ACCEPTED)){
            throw new ApiRuntimeException(CommonConstants.INVITATION_ACCEPTED, HttpStatus.BAD_REQUEST);
        }
        if(groupInvitationDAO.getStatus().equals(InvitationStatus.EXPIRED)){
            throw new ApiRuntimeException(CommonConstants.INVITATION_EXPIRED, HttpStatus.BAD_REQUEST);
        }
        if(!groupInvitationDAO.getReceiver().getUid().equals(userDAO.getUid())){
            return new BasicResDTO("This is not your Invitation", HttpStatus.BAD_REQUEST);
        }
        return groupService.addMember(groupInvitationDAO.getReceiver(), groupInvitationDAO.getInvitedTo());

    }

    public BasicResDTO acceptInvite(UUID token) {
        return null;
    }

    public BasicResDTO resendInvite(UUID invId, UserDAO userDAO) {
        return null;
    }
}
