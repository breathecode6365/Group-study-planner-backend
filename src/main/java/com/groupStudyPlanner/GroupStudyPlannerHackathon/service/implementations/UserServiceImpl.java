package com.groupStudyPlanner.GroupStudyPlannerHackathon.service.implementations;

import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.*;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.repository.DiscussionRepository;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.repository.UserRepository;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.repository.UserToGroupMapRepository;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.request.CreateForumReqDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.request.UpdatePasswordReqDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.request.UserSignupReqDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.request.UserUpdateReqDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.response.*;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.exception.ApiRuntimeException;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.service.UserService;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.utility.CommonConstants;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final DiscussionRepository discussionRepository;
    private final UserToGroupMapRepository userToGroupMapRepository;
    private final GroupServiceImpl groupService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public UserServiceImpl(UserRepository userRepository, DiscussionRepository discussionRepository, UserToGroupMapRepository userToGroupMapRepository, GroupServiceImpl groupService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.discussionRepository = discussionRepository;
        this.userToGroupMapRepository = userToGroupMapRepository;
        this.groupService = groupService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    public BasicResDTO signup(UserSignupReqDTO userSignupReqDTO){
        Optional<UserDAO> existingUser = userRepository.findByEmailId(userSignupReqDTO.emailId());
        if(existingUser.isPresent()){
            throw new ApiRuntimeException(CommonConstants.USER_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
        }
        UserDAO userDAO = UserDAO.builder()
                .emailId(userSignupReqDTO.emailId())
                .password(bCryptPasswordEncoder.encode(userSignupReqDTO.password()))
                .name(userSignupReqDTO.name())
                .build();
        userRepository.save(userDAO);
        return new BasicResDTO(CommonConstants.SIGNUP_SUCCESSFUL, HttpStatus.CREATED);
    }

    @Override
    public UserDataResDTO getMyProfile(String emailId) {
        UserDAO userDAO = userRepository.findByEmailId(emailId)
                .orElseThrow(() -> new ApiRuntimeException(CommonConstants.USER_NOT_FOUND, HttpStatus.NOT_FOUND));
        return new UserDataResDTO(userDAO.getName(), userDAO.getEmailId(), userDAO.getProfilePic());
    }

    public UserDAO findByEmailId(String name) {
        return userRepository.findByEmailId(name)
                .orElseThrow(() -> new ApiRuntimeException(CommonConstants.USER_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    public BasicResDTO updateMyProfile(UserDAO signedUser,UserUpdateReqDTO userUpdateReqDTO) {
        Optional<UserDAO> existingUser = userRepository.findByEmailId(userUpdateReqDTO.emailId());
        if(existingUser.isPresent()){
            throw new ApiRuntimeException(CommonConstants.USER_ALREADY_EXISTS, HttpStatus.BAD_REQUEST);
        }
        if(signedUser.getEmailId().equals(userUpdateReqDTO.emailId())){
            signedUser.setVerified(false);
        }
        signedUser.setEmailId(userUpdateReqDTO.emailId());
        signedUser.setName(userUpdateReqDTO.name());
        signedUser.setProfilePic(userUpdateReqDTO.profilePic());
        userRepository.save(signedUser);
        return new BasicResDTO(CommonConstants.PROFILE_UPDATE_SUCCESSFUL, HttpStatus.OK);
    }

    public UserDAO getByEmail(String name) {
        return userRepository.findByEmailId(name)
                .orElseThrow(() -> new ApiRuntimeException(CommonConstants.USER_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    @Override
    public BasicResDTO updatePassword(UserDAO userDAO, UpdatePasswordReqDTO updatePasswordReqDTO) {
        if(!bCryptPasswordEncoder.matches(updatePasswordReqDTO.oldPassword(), userDAO.getPassword())){
            throw new ApiRuntimeException(CommonConstants.INVALID_PASSWORD, HttpStatus.BAD_REQUEST);
        }
        userDAO.setPassword(bCryptPasswordEncoder.encode(updatePasswordReqDTO.newPassword()));
        userRepository.save(userDAO);
        return new BasicResDTO(CommonConstants.PASSWORD_UPDATE_SUCCESSFUL, HttpStatus.OK);
    }

    @Override
    public List<GetAllGroupsResDTO> getMyGroups(UserDAO userDAO) {
        List<UserToGroupMapDAO> data = userToGroupMapRepository.findAllByUserId(userDAO);
        List<GroupDAO> ConfidentialGroupData = new ArrayList<>();
        for(UserToGroupMapDAO userToGroupMapDAO : data){
            ConfidentialGroupData.add(userToGroupMapDAO.getGroupId());
        }
        List<GetAllGroupsResDTO> payload = new ArrayList<>(); // Create a mutable list
        for(GroupDAO groupDAO : ConfidentialGroupData){
            UserDataResDTO group = new UserDataResDTO(groupDAO.getCreatedBy().getName(), groupDAO.getCreatedBy().getEmailId(), groupDAO.getCreatedBy().getProfilePic());
            payload.add(new GetAllGroupsResDTO(groupDAO.getGid(),groupDAO.getName(), groupDAO.getDescription(), group));
        }
        return payload;
    }

    public BasicResDTO createForum(UserDAO user, GroupDAO groupDAO, CreateForumReqDTO createForumReqDTO) {
        DiscussionDAO discussionDAO = DiscussionDAO.builder()
                .groupId(groupDAO)
                .userId(user)
                .text(createForumReqDTO.discussion())
                .build();
        discussionRepository.save(discussionDAO);
        return new BasicResDTO(CommonConstants.DISCUSSION_POSTED, HttpStatus.CREATED);
    }

    public GenericResDTO<List<DiscussionDAO>> getForum(GroupDAO groupDAO) {
        List<DiscussionDAO> discussionDAOList = discussionRepository.findAllByGroupId(groupDAO);
        return new GenericResDTO<>(discussionDAOList, new BasicResDTO(CommonConstants.FORUM_FETCHED, HttpStatus.ACCEPTED));
    }

    public List<TaskDataResDTO> getMyTasks(UserDAO userDAO) {
        List<UserToGroupMapDAO> userToGroupMapDAOList = userToGroupMapRepository.findAllByUserId(userDAO);
        List<GroupDAO> groupDAOs = new ArrayList<>();
        for(UserToGroupMapDAO userToGroupMapDAO : userToGroupMapDAOList){
            groupDAOs.add(userToGroupMapDAO.getGroupId());
        }
        List<TaskDataResDTO> taskDAOList = new ArrayList<>();
        for(GroupDAO groupDAO : groupDAOs){
            taskDAOList.addAll(groupService.getTasks(groupDAO.getGid()));
        }
        return taskDAOList;
    }
}
