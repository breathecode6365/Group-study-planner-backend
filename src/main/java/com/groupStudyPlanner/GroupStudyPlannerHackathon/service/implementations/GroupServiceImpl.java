package com.groupStudyPlanner.GroupStudyPlannerHackathon.service.implementations;

import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.*;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.repository.GroupRepository;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.repository.ResourcesRepository;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.repository.TaskRepository;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.repository.UserToGroupMapRepository;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.request.CreateGroupReqDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.request.GroupUpdateReqDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.request.ProductReqDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.response.BasicResDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.response.ProductDataResDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.response.TaskDataResDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.response.UserDataResDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.exception.ApiRuntimeException;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.service.GroupService;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.utility.CommonConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final TaskRepository taskRepository;
    private final UserToGroupMapRepository userToGroupMapRepository;
    private final ProductService productService;
    private final ResourcesRepository resourcesRepository;

    public GroupServiceImpl(GroupRepository groupRepository, TaskRepository taskRepository, UserToGroupMapRepository userToGroupMapRepository, ProductService productService, ResourcesRepository resourcesRepository) {
        this.groupRepository = groupRepository;
        this.taskRepository = taskRepository;
        this.userToGroupMapRepository = userToGroupMapRepository;
        this.productService = productService;
        this.resourcesRepository = resourcesRepository;
    }
    public BasicResDTO createGroup(UserDAO user, CreateGroupReqDTO createGroupReqDTO) {
        Optional<GroupDAO> existingGroup = groupRepository.findByName(createGroupReqDTO.name());
        if(existingGroup.isPresent()) {
            throw new ApiRuntimeException(CommonConstants.GROUP_EXIST, HttpStatus.BAD_REQUEST);
        }
        GroupDAO groupDAO = GroupDAO.builder()
                .name(createGroupReqDTO.name())
                .description(createGroupReqDTO.description())
                .createdBy(user)
                .build();
        groupDAO = groupRepository.save(groupDAO);
        UserToGroupMapDAO userToGroupMapDAO = UserToGroupMapDAO.builder()
                .groupId(groupDAO)
                .userId(user)
                .build();
        userToGroupMapRepository.save(userToGroupMapDAO);
        return new BasicResDTO(CommonConstants.GROUP_CREATED, HttpStatus.CREATED);

    }

    public BasicResDTO updateGroup(UserDAO user, GroupUpdateReqDTO groupUpdateReqDTO, UUID gid) {
        GroupDAO groupDAO = groupRepository.findById(gid)
                .orElseThrow(() -> new ApiRuntimeException(CommonConstants.GROUP_NOT_FOUND, HttpStatus.NOT_FOUND));
        if(!groupDAO.getCreatedBy().getUid().equals(user.getUid())){
            throw new ApiRuntimeException(CommonConstants.UNAUTHORISED_OPERATION, HttpStatus.UNAUTHORIZED);
        }
        groupDAO.setName(groupUpdateReqDTO.name());
        groupDAO.setDescription(groupUpdateReqDTO.description());
        groupRepository.save(groupDAO);
        return new BasicResDTO(CommonConstants.GROUP_UPDATED, HttpStatus.OK);
    }

    public GroupDAO findById(UUID gid) {
        return groupRepository.findById(gid)
                .orElseThrow(() -> new ApiRuntimeException(CommonConstants.GROUP_NOT_FOUND, HttpStatus.NOT_FOUND));
    }

    public BasicResDTO addMember(UserDAO user, GroupDAO groupDAO) {
        UserToGroupMapDAO userToGroupMapDAO = UserToGroupMapDAO.builder()
                .groupId(groupDAO)
                .userId(user)
                .build();
        userToGroupMapRepository.save(userToGroupMapDAO);
        return new BasicResDTO(CommonConstants.MEMBER_ADDED, HttpStatus.OK);
    }

    @Override
    public boolean verifyMember(UserDAO user, GroupDAO payload) {
        return userToGroupMapRepository.findByGroupId(payload).stream().anyMatch(userToGroupMapDAO -> userToGroupMapDAO.getUserId().getUid().equals(user.getUid()));
    }

    @Override
    public List<UserDataResDTO> getMembers(UUID gid, UserDAO userDAO) {
        GroupDAO groupDAO = groupRepository.findById(gid)
                .orElseThrow(() -> new ApiRuntimeException(CommonConstants.GROUP_NOT_FOUND, HttpStatus.NOT_FOUND));
        if(!verifyMember(userDAO, groupDAO)){
            throw new ApiRuntimeException(CommonConstants.UNAUTHORISED_OPERATION, HttpStatus.UNAUTHORIZED);
        }
        List<UserToGroupMapDAO> userToGroupMapDAOList = userToGroupMapRepository.findByGroupId(groupDAO);
        List<UserDataResDTO> payload =  new ArrayList<>();
        for(UserToGroupMapDAO userToGroupMapDAO : userToGroupMapDAOList){
            payload.add(new UserDataResDTO(userToGroupMapDAO.getUserId().getName(), userToGroupMapDAO.getUserId().getEmailId(), userToGroupMapDAO.getUserId().getProfilePic()));
        }
        return payload;
    }

    @Override
    public void evaluateOverAllProgress(GroupDAO groupDAO) {
        List<TaskDAO> allTasks = taskRepository.findAllByGroupId(groupDAO);
        double sum = allTasks.stream().mapToDouble(TaskDAO::getProgress).sum();
        groupDAO.setOverallProgress((sum/allTasks.size())*100);
        groupRepository.save(groupDAO);
    }

    @Override
    @Transactional
    public List<TaskDataResDTO> getTasks(UUID gid) {
        GroupDAO groupDAO = groupRepository.findById(gid)
                .orElseThrow(() -> new ApiRuntimeException(CommonConstants.GROUP_NOT_FOUND, HttpStatus.NOT_FOUND));
        List<TaskDAO> taskDAOS = taskRepository.findAllByGroupId(groupDAO);
        List<TaskDataResDTO> payload = new ArrayList<>();
        for(TaskDAO taskDAO : taskDAOS){
            payload.add(new TaskDataResDTO(taskDAO.getTid(), taskDAO.getName(), taskDAO.getDescription(), taskDAO.getCreatedOn(), taskDAO.getDeadline(), taskDAO.getProgress(), taskDAO.getStatus()));
        }
        return payload;
    }

    public BasicResDTO addResource(ProductReqDTO productReqDTO, UserDAO user, GroupDAO groupDAO) {
        ResourcesDAO resourcesDAO = ResourcesDAO.builder()
                .groupId(groupDAO)
                .resourceName(productReqDTO.name())
                .link(productReqDTO.url())
                .build();
        resourcesRepository.save(resourcesDAO);
        return new BasicResDTO(CommonConstants.RESOURCE_ADDED, HttpStatus.CREATED);
    }


    @Override
    public List<ProductDataResDTO> getResources(GroupDAO groupDAO) {
        List<ResourcesDAO> resourcesDAOList = resourcesRepository.findAllByGroupId(groupDAO);
        List<ProductDataResDTO> payload = new ArrayList<>();
        for(ResourcesDAO resourcesDAO : resourcesDAOList){
            payload.add(new ProductDataResDTO(resourcesDAO.getResourceName(), resourcesDAO.getLink()));
        }
        return payload;
    }
}
