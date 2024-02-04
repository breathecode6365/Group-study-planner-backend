package com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.response;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Builder
public record ExceptionResDTO(
     String message,
     Date timestamp,
     String path,
     HttpStatus httpStatus

){
}

