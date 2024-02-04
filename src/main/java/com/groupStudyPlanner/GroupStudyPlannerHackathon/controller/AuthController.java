package com.groupStudyPlanner.GroupStudyPlannerHackathon.controller;

import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.request.UserLoginReqDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.request.UserSignupReqDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.response.BasicResDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.response.GenericResDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.dto.response.TokenResDTO;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.service.TokenService;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.service.UserService;
import com.groupStudyPlanner.GroupStudyPlannerHackathon.utility.CommonConstants;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userService = userService;
    }
    @GetMapping("hello")
    public String hello() {
        return "Hello World!";
        }

    @PostMapping("login")
    GenericResDTO<TokenResDTO> login(@RequestBody @Valid UserLoginReqDTO userLoginReqDTO) {
        var credentials = new UsernamePasswordAuthenticationToken(userLoginReqDTO.emailId(),
                userLoginReqDTO.password());
        var authentication = authenticationManager.authenticate(credentials);
        var payload = tokenService.generateToken(authentication);
        return new GenericResDTO<>(payload, new BasicResDTO(CommonConstants.LOGIN_SUCCESSFUL, HttpStatus.OK));
    }
    @GetMapping("renew-token")
    public GenericResDTO<TokenResDTO> refresh(Authentication authentication) {
        var payload = tokenService.generateToken(authentication);
        return new GenericResDTO<>(payload, new BasicResDTO(CommonConstants.TOKEN_RENEWED, HttpStatus.OK));
    }

    @PostMapping("signup")
    public BasicResDTO signup(@RequestBody @Valid UserSignupReqDTO userSignupReqDTO) {
        return userService.signup(userSignupReqDTO);
    }


}
