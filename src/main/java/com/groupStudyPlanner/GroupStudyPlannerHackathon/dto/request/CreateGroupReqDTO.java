package com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record CreateGroupReqDTO(
        @NotEmpty
        String name,
        @NotEmpty
        String description
) {
}
