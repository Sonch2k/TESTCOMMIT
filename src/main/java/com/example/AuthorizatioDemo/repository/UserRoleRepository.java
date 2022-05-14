package com.example.AuthorizatioDemo.repository;

import com.example.AuthorizatioDemo.entity.User;
import com.example.AuthorizatioDemo.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    List<UserRole> findByUser(User user);
}
