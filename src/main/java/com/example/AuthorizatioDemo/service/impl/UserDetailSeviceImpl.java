package com.example.AuthorizatioDemo.service.impl;

import com.example.AuthorizatioDemo.entity.Role;
import com.example.AuthorizatioDemo.entity.User;
import com.example.AuthorizatioDemo.entity.UserRole;
import com.example.AuthorizatioDemo.repository.UserReposicoty;
import com.example.AuthorizatioDemo.repository.UserRoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserDetailSeviceImpl implements UserDetailsService {
    private UserReposicoty userReposicoty;
    private UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userReposicoty.findByUsername(username);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("Not found User ^^:" + username);
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        List<UserRole> userRoles = userRoleRepository.findByUser(user);
        for (UserRole userRole : userRoles) {
            String role = "";
            try {
                Role role1 = userRole.getRole();
                role = role1.getRoleName();
            } catch (Exception e) {
                System.out.println("Loi get role" + e.getMessage());
            }
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role);
            grantedAuthorities.add(grantedAuthority);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername()
                , user.getPassword()
                , grantedAuthorities
        );
    }
}
