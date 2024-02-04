package com.groupStudyPlanner.GroupStudyPlannerHackathon.service;

import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.DiscussionDAO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.GroupDAO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.UserDAO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.request.CreateForumReqDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.request.UpdatePasswordReqDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.request.UserSignupReqDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.request.UserUpdateReqDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.response.*;

import java.util.List;

public interface UserService {
    BasicResDTO signup(UserSignupReqDTO userSignupReqDTO);

    UserDataResDTO getMyProfile(String emailId);

    UserDAO findByEmailId(String name);

    BasicResDTO updateMyProfile(UserDAO signedUser,UserUpdateReqDTO userUpdateReqDTO);

    UserDAO getByEmail(String name);

    BasicResDTO updatePassword(UserDAO userDAO, UpdatePasswordReqDTO updatePasswordReqDTO);

    List<GetAllGroupsResDTO> getMyGroups(UserDAO userDAO);

    BasicResDTO createForum(UserDAO user, GroupDAO groupDAO, CreateForumReqDTO createForumReqDTO);
    GenericResDTO<List<DiscussionDAO>> getForum(GroupDAO groupDAO);

    List<TaskDataResDTO> getMyTasks(UserDAO userDAO);
}
