package com.todo.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todo.app.dto.LoginRequest;
import com.todo.app.dto.LoginResponse;
import com.todo.app.dto.UserCreateDTO;
import com.todo.app.dto.UserNameDTO;
import com.todo.app.entity.User;
import com.todo.app.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    
    @PostMapping("/new")
    public ResponseEntity<UserNameDTO> create(@RequestBody UserCreateDTO userCreateDTO) {
        User user = userService.createUser(userCreateDTO);
        UserNameDTO dto = UserNameDTO.convertToDTO(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
    
    /// LOGIN QILISH TIME
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
            System.out.println("Session ID: " + session.getId()); // Sessiya ID log
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String fullName = userService.findFullNameByUsername(userDetails.getUsername());
            return ResponseEntity.ok(new LoginResponse(fullName));
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Username yoki parol noto‘g‘ri");
        }
    }
    

    
}
