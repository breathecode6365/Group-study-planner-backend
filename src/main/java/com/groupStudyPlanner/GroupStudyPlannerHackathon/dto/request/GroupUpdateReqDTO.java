package com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.request;

import com.groupStudyPlanner.GroupStudyPlannerHackathon.dao.GroupDAO;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotEmpty;

public record GroupUpdateReqDTO(
        @NotEmpty(message = "name not provided")
        String name,

        @NotEmpty(message = "Description not provided")
        String description
) {
}
