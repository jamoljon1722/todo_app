package com.todo.app.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoRequest {
    private String todoName;
    private String description;
}
