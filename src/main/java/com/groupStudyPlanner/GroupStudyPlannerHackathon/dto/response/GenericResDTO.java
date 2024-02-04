package com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.response;

public record GenericResDTO<D>(
        D data,
        BasicResDTO basicResDTO
) {

}
