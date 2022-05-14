package com.example.AuthorizatioDemo.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserRequest {
    private String username;
    private String password;
}
