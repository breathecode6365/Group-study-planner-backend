package com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.response;

import org.springframework.web.bind.annotation.RestController;


public record UserDataResDTO(
        String name,
        String emailId,
        String profilePic
) {
}
