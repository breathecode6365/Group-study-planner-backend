package com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.repository;

import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.GroupDAO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.GroupInvitationDAO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.UserDAO;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GroupInvitationRepository extends JpaRepository<GroupInvitationDAO, UUID> {

    List<GroupInvitationDAO> findAllByInvitedTo(GroupDAO groupDAO);
}
