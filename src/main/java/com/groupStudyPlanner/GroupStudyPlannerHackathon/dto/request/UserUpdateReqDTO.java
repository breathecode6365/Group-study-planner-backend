package com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.NonNull;

public record UserUpdateReqDTO(
        @NotEmpty(message = "Name not provided")
        String name,
        @NotEmpty(message = "Email not provided")
        String emailId,
        @NotEmpty(message = "Profile picture not provided")
        @NonNull
        String profilePic
) {
}
