package com.example.AuthorizatioDemo.entity;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@Table(name = "role_permission", uniqueConstraints = {@UniqueConstraint(name = "ROLE_PERMISSION_UK", columnNames = {"role_id", "permission_id"})})
@Data
@EntityListeners(AuditingEntityListener.class)
public class RolePermission {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    @JoinColumn(name = "role_id")
    @ManyToOne
    private Role role;
    @JoinColumn(name = "permission_id")
    @ManyToOne
    private Permission permission;
}
