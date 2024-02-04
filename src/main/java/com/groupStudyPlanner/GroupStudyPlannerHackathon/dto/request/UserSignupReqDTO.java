package com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public record UserSignupReqDTO(
        @NotEmpty
        String name,
        @NotEmpty
        String password,
        @NotEmpty
        @Email
        String emailId
) {
}
