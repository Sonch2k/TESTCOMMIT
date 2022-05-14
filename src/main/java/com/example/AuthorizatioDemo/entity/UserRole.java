package com.example.AuthorizatioDemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "user_role", uniqueConstraints = {@UniqueConstraint(name = "USER_ROLE_UK", columnNames = {"user_id", "role_id"})})
@Data
@EntityListeners(AuditingEntityListener.class)
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
