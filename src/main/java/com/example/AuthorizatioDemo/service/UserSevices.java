package com.example.AuthorizatioDemo.service;


import com.example.AuthorizatioDemo.model.request.UserRequest;
import com.example.AuthorizatioDemo.model.request.UserRoleRequest;

import javax.servlet.http.HttpServletRequest;

public interface UserSevices {
    public String registerSevices(UserRequest userRequest);

    public String loginSevices(UserRequest userRequest);

    public String changeRoleSevices(UserRoleRequest userRoleRequest, HttpServletRequest request);
}
