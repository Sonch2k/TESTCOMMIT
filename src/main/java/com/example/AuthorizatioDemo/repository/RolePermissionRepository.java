package com.example.AuthorizatioDemo.repository;

import com.example.AuthorizatioDemo.entity.Permission;
import com.example.AuthorizatioDemo.entity.Role;
import com.example.AuthorizatioDemo.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {
    List<RolePermission> findByRole(Role role);
}
