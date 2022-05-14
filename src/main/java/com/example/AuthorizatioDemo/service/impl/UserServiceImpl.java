package com.example.AuthorizatioDemo.service.impl;

import com.example.AuthorizatioDemo.entity.*;
import com.example.AuthorizatioDemo.model.request.UserRequest;
import com.example.AuthorizatioDemo.model.request.UserRoleRequest;
import com.example.AuthorizatioDemo.repository.RolePermissionRepository;
import com.example.AuthorizatioDemo.repository.RoleRepository;
import com.example.AuthorizatioDemo.repository.UserReposicoty;
import com.example.AuthorizatioDemo.repository.UserRoleRepository;
import com.example.AuthorizatioDemo.service.UserSevices;
import com.example.AuthorizatioDemo.utils.JwtUtils;
import com.sun.rowset.internal.Row;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserSevices {
    private UserReposicoty userReposicoty;
    private PasswordEncoder pe;
    private AuthenticationManager authenticationManager;
    private UserRoleRepository userRoleRepository;
    private RoleRepository roleRepository;
    private JwtUtils jwtUtils;
    private RolePermissionRepository rolePermissionRepository;

    @Override
    public String registerSevices(UserRequest userRequest) {
        User user = userReposicoty.findByUsername(userRequest.getUsername());
        if (Objects.isNull(user)) {
            User userEntity = new User();
            BeanUtils.copyProperties(userRequest, userEntity);
            userEntity.setPassword(pe.encode(userEntity.getPassword()));
            userReposicoty.save(userEntity);
            UserRole userRole = new UserRole();
            userRole.setUser(userEntity);
            userRole.setRole(roleRepository.findByRoleName("ROLE_USER"));
            userRoleRepository.save(userRole);
            return "Register successfully Account: " + userEntity.getUsername();
        }
        return "Account Existed,try again!";
    }

    @Override
    public String loginSevices(UserRequest userRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userRequest.getUsername()
                        , userRequest.getPassword()
                )
        );
        String jwt = jwtUtils.generateToken(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwt;
    }

    @Override
    public String changeRoleSevices(UserRoleRequest userRoleRequest, HttpServletRequest request) {
        if (Objects.nonNull(checkPermission(request))) return checkPermission(request);
        User user = userReposicoty.findByUsername(userRoleRequest.getUsername());
        if (Objects.isNull(user)) {
            return "Account dont existed,try again!";
        }
        List<UserRole> userRoles = userRoleRepository.findByUser(user);
        for (UserRole model : userRoles) {
            try {
                if (model.getRole().getRoleName().contains(userRoleRequest.getRoleChange())) {
                    return "Role existed with this account,try anothor role!";
                }
            } catch (Exception e) {

            }

        }
        UserRole userRole = new UserRole();
        userRole.setUser(user);
        Role role = roleRepository.findByRoleName("ROLE_" + userRoleRequest.getRoleChange());
        if (Objects.isNull(role)) {
            return "Role is not existed,try again!";
        }
        userRole.setRole(role);
        userRoleRepository.save(userRole);
        return "Successfully change new role";
    }

    public String checkPermission(HttpServletRequest request) {
        int checkfalse = 1;
        String token = jwtUtils.parseJWT(request);
        if (Objects.isNull(token)) return "Token must not empty";
        String username = jwtUtils.getName(token);
        User user = userReposicoty.findByUsername(username);
        if (Objects.isNull(user)) return "not authentication user not existed";
        List<UserRole> roles = userRoleRepository.findByUser(user);
        for (UserRole modelRole : roles) {
            List<RolePermission> permissions = rolePermissionRepository.findByRole(modelRole.getRole());
            for (RolePermission modelPermission : permissions) {
                if (request.getRequestURL().toString().contains(modelPermission.getPermission().getPermisstionLink())) {
                    checkfalse = 0;
                    break;
                }
            }
        }
        if (checkfalse == 1) {
            return "false permission";
        } else return null;
    }
}
