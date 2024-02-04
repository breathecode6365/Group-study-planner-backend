package com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.response;

import com.groupStudyPlanner.GroupStudyPlannerHackathon.enumerators.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskDataResDTO(
        UUID taskId,
        String name,
        String description,
        LocalDateTime createdOn,
        long deadline,
        double progress,
        TaskStatus status
) {
}
