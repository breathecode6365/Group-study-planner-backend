package com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.request;

public record UpdatePasswordReqDTO(
        String oldPassword,
        String newPassword
) {

}
