package com.todo.app.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.todo.app.dto.UserCreateDTO;
import com.todo.app.entity.User;
import com.todo.app.exception.DataAlreadyExistsException;
import com.todo.app.exception.DataNotFoundException;
import com.todo.app.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
    public User createUser(UserCreateDTO userCreateDTO) {
        userRepository.findByUsername(userCreateDTO.getUsername())
        .ifPresent(user -> {
            throw new DataAlreadyExistsException("Foydalanuvchi allaqachon mavjud");
        });

        User user = new User();
        user.setFullName(userCreateDTO.getFullName());
        user.setUsername(userCreateDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userCreateDTO.getPassword()));
        return userRepository.save(user);
    }

        public String findFullNameByUsername(String username) {
            User user = userRepository.findByUsername(username).orElseThrow(
                () -> new DataNotFoundException("Foydalanuvchi topilmadi")
            );

            return user.getFullName();
        }
}
