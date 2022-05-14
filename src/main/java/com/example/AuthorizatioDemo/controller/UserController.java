package com.example.AuthorizatioDemo.controller;

import com.example.AuthorizatioDemo.model.request.UserRequest;
import com.example.AuthorizatioDemo.model.request.UserRoleRequest;
import com.example.AuthorizatioDemo.service.UserSevices;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@AllArgsConstructor
public class UserController {
    private UserSevices userSevices;

    @PostMapping("/register")
    public String registerPerformance(@RequestBody UserRequest userRequest) {
        return userSevices.registerSevices(userRequest);
    }

    @PostMapping("/auth")
    public String loginPerformance(@RequestBody UserRequest userRequest) {
        return userSevices.loginSevices(userRequest);
    }

    @PostMapping("/changeUserRole")
    public String changeRolePerformance(@RequestBody UserRoleRequest userRoleRequest, HttpServletRequest request) {
        System.out.println(request.getHeader("Authorization"));
        return userSevices.changeRoleSevices(userRoleRequest, request);
    }

}
