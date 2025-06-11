package com.todo.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String fullName;

    public LoginResponse(String fullName) {
        this.fullName = fullName;
    }

}