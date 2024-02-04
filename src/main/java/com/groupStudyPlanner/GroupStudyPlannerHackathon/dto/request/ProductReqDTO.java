package com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.request;

import jakarta.validation.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

public record ProductReqDTO(
        @NotEmpty(message = "Name not provided")
        String name,
        @NotEmpty(message = "URL not provided")
        String url
) {
}
