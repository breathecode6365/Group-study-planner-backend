package com.groupStudyPlanner.GroupStudyPlannerHackathon.service;

import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.response.TokenResDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.utility.CommonConstants;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

public interface TokenService {
    TokenResDTO generateToken(Authentication authentication);
    String getJwtToken(String subject, String scope, Instant now, Instant expiryTime);
}
