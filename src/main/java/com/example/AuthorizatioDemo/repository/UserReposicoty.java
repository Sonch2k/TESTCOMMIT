package com.example.AuthorizatioDemo.repository;

import com.example.AuthorizatioDemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReposicoty extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
