package com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record UserInviteReqDTO(
        @NotEmpty(message = "Email not provided")
        String emailId
) {
}
