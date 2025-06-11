package com.todo.app.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateDTO {
    private String fullName;
    private String username;
    private String password;
}
