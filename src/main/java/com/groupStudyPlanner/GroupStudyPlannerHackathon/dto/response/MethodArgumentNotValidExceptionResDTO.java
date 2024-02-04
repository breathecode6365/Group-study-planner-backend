package com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.response;

import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.Map;

public record MethodArgumentNotValidExceptionResDTO(
         String message,
         Date timestamp,
         String path,
         HttpStatus httpStatus,
         Map<String, Object> errors
    ){
}
