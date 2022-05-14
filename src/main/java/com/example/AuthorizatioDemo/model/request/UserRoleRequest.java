package com.example.AuthorizatioDemo.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserRoleRequest {
    private String username;
    private String roleChange;
}
