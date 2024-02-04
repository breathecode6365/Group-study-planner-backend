package com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.response;

import jakarta.persistence.Lob;

import java.util.UUID;

public record GetAllGroupsResDTO(
        UUID id,
        String name,
        String description,
        UserDataResDTO owner
) {
}
