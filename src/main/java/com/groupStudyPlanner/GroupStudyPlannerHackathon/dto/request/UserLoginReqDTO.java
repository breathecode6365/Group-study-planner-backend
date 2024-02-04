package com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.request;


import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.NoArgsConstructor;

public record UserLoginReqDTO(
        @Email(message = "Invalid email or Email not provided")
        String emailId,
        @NotEmpty(message = "Password not provided")
        String password
) {
}
