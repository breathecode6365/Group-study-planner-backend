package com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotEmpty;

import java.sql.Date;
import java.time.LocalDateTime;

public record CreateTaskReqDTO(
        @NotEmpty
        String taskName,

        @NotEmpty
        String taskDescription,

        long taskDeadline

) {

}
