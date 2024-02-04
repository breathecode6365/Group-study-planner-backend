package com.groupStudyPlanner.GroupStudyPlannerHackathon.service;

import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.GroupDAO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.TaskDAO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.UserDAO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.request.CreateGroupReqDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.request.GroupUpdateReqDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.request.ProductReqDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.response.BasicResDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.response.ProductDataResDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.response.TaskDataResDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.response.UserDataResDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface GroupService {
    BasicResDTO createGroup(UserDAO user, CreateGroupReqDTO createGroupReqDTO);

    BasicResDTO updateGroup(UserDAO user, GroupUpdateReqDTO groupUpdateReqDTO, UUID gid);

    GroupDAO findById(UUID gid);

    BasicResDTO addMember(UserDAO user, GroupDAO groupDAO);

    boolean verifyMember(UserDAO user, GroupDAO payload);

    List<UserDataResDTO> getMembers(UUID gid, UserDAO userDAO);

    void evaluateOverAllProgress(GroupDAO groupDAO);

    List<TaskDataResDTO> getTasks(UUID gid);

    public BasicResDTO addResource(ProductReqDTO productReqDTO, UserDAO user, GroupDAO groupDAO);

    List<ProductDataResDTO> getResources(GroupDAO groupDAO);
}
