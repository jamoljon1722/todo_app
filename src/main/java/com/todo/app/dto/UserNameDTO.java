package com.todo.app.dto;
import com.todo.app.entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNameDTO {
    private String fullName;
    private String username;


    public static UserNameDTO convertToDTO(User user) {
        UserNameDTO userNameDTO = new UserNameDTO();
        userNameDTO.setFullName(user.getFullName());
        userNameDTO.setUsername(user.getUsername());

        return userNameDTO;
    }
}
